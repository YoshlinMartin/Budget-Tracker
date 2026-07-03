package com.example.budgettracker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgettracker.models.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddExpense : AppCompatActivity() {

    private lateinit var spinnerCategory: Spinner
    private lateinit var etTitle: EditText
    private lateinit var etAmount: EditText
    private lateinit var etDate: EditText
    private lateinit var etDescription: EditText
    private lateinit var imageView: ImageView
    private lateinit var btnCamera: Button
    private lateinit var btnGallery: Button
    private lateinit var btnSaveExpense: Button

    private var imageUri: Uri? = null

    private val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expense)

        // UI refs
        imageView = findViewById(R.id.imageView)
        btnCamera = findViewById(R.id.btnCamera)
        btnGallery = findViewById(R.id.btnGallery)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)

        etTitle = findViewById(R.id.etTitle)
        etAmount = findViewById(R.id.etAmount)
        etDate = findViewById(R.id.etDate)
        etDescription = findViewById(R.id.etDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)

        // Pick image from gallery (LOCAL ONLY)
        btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        // Save expense (NO FIREBASE STORAGE)
        btnSaveExpense.setOnClickListener {
            saveExpense()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Handle image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imageView.setImageURI(imageUri) // ONLY LOCAL DISPLAY
        }
    }

    // SAVE ONLY TEXT DATA TO FIREBASE
    private fun saveExpense() {

        val title = etTitle.text.toString()
        val amountText = etAmount.text.toString()
        val date = etDate.text.toString()
        val description = etDescription.text.toString()
        val category = spinnerCategory.selectedItem.toString()

        if (title.isEmpty() || amountText.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDouble()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        val database = FirebaseDatabase.getInstance().reference
        val expenseId = database.push().key!!

        // IMAGE IS NOT SAVED ONLINE ANYMORE
        val expense = Expense(
            expenseId = expenseId,
            title = title,
            amount = amount,
            category = category,
            date = date,
            description = description
        )

        database.child("Expenses")
            .child(userId)
            .child(expenseId)
            .setValue(expense)
            .addOnSuccessListener {
                Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save expense", Toast.LENGTH_SHORT).show()
            }
    }
}