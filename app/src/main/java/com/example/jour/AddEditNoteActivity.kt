package com.example.jour

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jour.MVVM.Place
import com.example.jour.MVVM.PlaceViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var backButton: FloatingActionButton
    lateinit var editTitle: EditText
    lateinit var editDesc: EditText
    lateinit var rating: RatingBar
    lateinit var saveButton: FloatingActionButton
    lateinit var viewModel: PlaceViewModel
    lateinit var addImageButton: FloatingActionButton
    lateinit var theimage: ImageView
    private var imageURI: Uri? = null
    private var bmp: Bitmap? = null
    var noteID = -1
    private var updateStartDateButton: Button? = null
    private val tripStartDateString: String? = null
    private var year = 0
    private var month = 0
    private var day = 0
    private var updateStartDateTextView: TextView? = null
    private var updateEndDateButton: Button? = null
    private var updateEndDateTextView: TextView? = null
    private val tripEndDateString: String? = null
    private var isStartDate = true // Flag to determine if the selected date is for start or end


    companion object {
        const val IMAGE_REQ_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        editTitle = findViewById(R.id.editNoteTitle)
        editDesc = findViewById(R.id.editNoteDescription)
        rating = findViewById(R.id.ratingBar)
        val editDate = findViewById<EditText>(R.id.updateStartDateTextView)
//        updateEndDateButton = findViewById<Button>(R.id.updateEndDateButton)
        //updateEndDateTextView = findViewById<TextView>(R.id.updateStartDateTextView2)

        val rating = findViewById<RatingBar>(R.id.ratingBar)
        saveButton = findViewById(R.id.jourSaveButton)
        backButton = findViewById(R.id.backButton)
        addImageButton = findViewById(R.id.jourAddImgButton)
        theimage = findViewById(R.id.imageView1)

        updateStartDateButton = findViewById<Button>(R.id.updateStartDateButton)

        updateStartDateTextView = findViewById<TextView>(R.id.updateStartDateTextView)


        val selectedLocation = intent.getParcelableExtra<LatLng>("selectedLocation")

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PlaceViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType == "Edit") {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDate = intent.getStringExtra("noteDate")
            val noteDesc = intent.getStringExtra("noteDescription")
            val noteRating = intent.getIntExtra("noteRating", 0)

            noteID = intent.getIntExtra("noteID", -1)
            editTitle.setText(noteTitle)
            editDate.setText(noteDate)
            editDesc.setText(noteDesc)
            rating.rating = noteRating.toFloat()

            // Getting Bitmap Image
            val filename = intent.getStringExtra("noteImage")
            try {
                val receiveImage: FileInputStream = this.openFileInput(filename)
                bmp = BitmapFactory.decodeStream(receiveImage)
                theimage.setImageBitmap(bmp)
                receiveImage.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        saveButton.setOnClickListener {
            val noteTitle = editTitle.text.toString()
            val noteDesc = editDesc.text.toString()
            val noteRating = rating.rating.toInt()
            val startDate = updateStartDateTextView?.text.toString() // Get the selected date

            if (noteType == "Edit") {
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty() && startDate.isNotEmpty()) {
                    val updateNote = Place(noteTitle, noteDesc, startDate, noteRating, bmp) // tu był noteDate
                    updateNote.id = noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                } else {
                    Toast.makeText(this, "Please fill the rows!", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty() && startDate.isNotEmpty()) {
                    viewModel.addNote(Place(noteTitle, noteDesc, startDate, noteRating, bmp)) // tu był noteDate
                    Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                } else {
                    Toast.makeText(this, "Please fill the rows!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this@AddEditNoteActivity, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        addImageButton.setOnClickListener {
            pickImageGallery()
        }
        updateStartDateButton?.setOnClickListener { isStartDate = true
            onClickPickStartDate(it) }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQ_CODE && resultCode == RESULT_OK) {
            imageURI = data?.data
            if (imageURI != null) {
                if (data != null) {
                    theimage.setImageURI(data.data)
                }
                try {
                    bmp = MediaStore.Images.Media.getBitmap(contentResolver, imageURI)
                    theimage.setImageBitmap(bmp)
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }
        }
    }

    fun showDates() {
        updateStartDateTextView?.setText(tripStartDateString)
    }


//    fun onClickPickStartDate(view: View?) {
//        val calendar = Calendar.getInstance()
//        year = calendar[Calendar.YEAR]
//        month = calendar[Calendar.MONTH]
//        day = calendar[Calendar.DAY_OF_MONTH]
//        calendar.time
//        val datePickerDialog = DatePickerDialog(this,
//            { _, year, month, dayOfMonth ->
//                if (isStartDate) {
//                    updateStartDateTextView?.text = "$dayOfMonth-${month + 1}-$year"
//                    isStartDate = false
//                    onClickPickStartDate(view) // Show the date picker again for the end date
//                } else {
//                    updateStartDateTextView?.text = "${updateStartDateTextView?.text} - $dayOfMonth-${month + 1}-$year"
//                }
//            },
//            year,
//            month,
//            day
//        )
//        datePickerDialog.show()
//    }
fun onClickPickStartDate(view: View?) {
    val calendar = Calendar.getInstance()
    year = calendar[Calendar.YEAR]
    month = calendar[Calendar.MONTH]
    day = calendar[Calendar.DAY_OF_MONTH]
    calendar.time
    val datePickerDialog = DatePickerDialog(this,
        { _, year, month, dayOfMonth ->
            if (isStartDate) {
                updateStartDateTextView?.text = "$dayOfMonth-${month + 1}-$year"
                isStartDate = false
                onClickPickStartDate(view) // Pokaż ponownie DatePicker dla daty końcowej
            } else {
                val startDate = getStartDateFromTextView()
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Sprawdź, czy data końcowa jest późniejsza niż data początkowa
                if (selectedDate.before(startDate)) {
                    Toast.makeText(this, "Data końcowa nie może być wcześniejsza niż data początkowa", Toast.LENGTH_SHORT).show()
                } else {
                    updateStartDateTextView?.text = "${updateStartDateTextView?.text} - $dayOfMonth-${month + 1}-$year"
                }
            }
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

    // Metoda pomocnicza do uzyskania daty początkowej z TextView
    private fun getStartDateFromTextView(): Calendar {
        val startDateText = updateStartDateTextView?.text.toString().split(" - ")[0]
        return parseDate(startDateText)
    }

    // Metoda pomocnicza do parsowania daty z tekstu na obiekt Calendar
    private fun parseDate(dateString: String): Calendar {
        val dateParts = dateString.split("-")
        val year = dateParts[2].toInt()
        val month = dateParts[1].toInt() - 1
        val day = dateParts[0].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar
    }

}
