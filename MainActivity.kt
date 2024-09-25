package com.example.cosasaccesodatos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cosasaccesodatos.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding2: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityMainBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding2.root)
        //setContentView(R.layout.activity_main2)
        val db2 = FirebaseFirestore.getInstance()

        // +++ Botón Dar Alta +++
        binding2.btalta.setOnClickListener {
            val codigo = binding2.etid.text.toString().trim().toIntOrNull()
            val descrip = binding2.etdescripcion.text.toString().trim()
            val prec = binding2.etprecio.text.toString().trim().toIntOrNull()

            if (codigo == null || descrip.isEmpty() || prec == null) {
                binding2.etconsulta.setText("ERROR: EL objeto a añadir no está bien definido")
                return@setOnClickListener
            }

            val data = hashMapOf(
                "descripcion" to descrip,
                "id_producto" to codigo,
                "precio" to prec
            )

            db2.collection("Productos")
                .whereEqualTo("id_producto", codigo)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        db2.collection("Productos")
                            .document(codigo.toString())
                            .set(data)
                            .addOnSuccessListener {
                                binding2.etconsulta.setText("Producto añadido correctamente")
                            }
                            .addOnFailureListener {
                                binding2.etconsulta.setText("Error al añadir el producto")
                            }
                    } else {
                        binding2.etconsulta.setText("Error: El producto ya existe")
                    }
                }
                .addOnFailureListener {
                    binding2.etconsulta.setText("Error al verificar el producto")
                }
        }
        // --- Botón Dar Alta ---
        // +++ Botón Consultar por código +++
        binding2.btConsultaCodigo.setOnClickListener {
            val codigo = binding2.etid.text.toString().trim()
            if (codigo.isEmpty()) {
                binding2.etconsulta.setText("El campo de búsqueda está vacio")
                return@setOnClickListener
            }
            db2.collection("Productos").document(codigo)
                .get()
                .addOnSuccessListener { document ->
                    if(document.exists()) {
                        val dataOutput = "${document.id} : ${document.data}"
                        binding2.etconsulta.setText(dataOutput)
                    } else {
                        binding2.etconsulta.setText("No se encontró el producto con código $codigo")
                    }
                }
                .addOnFailureListener {
                    binding2.etconsulta.setText("Error al consultar el producto")
                }
        }
        // --- Botón Consultar por código ---
        // +++ Botón Consultar por descripción +++
        binding2.btConsultaDesc.setOnClickListener {
            val descrip = binding2.etdescripcion.text.toString().trim()
            if (descrip.isEmpty()) {
                binding2.etconsulta.setText("El campo de búsqueda está vacío")
                return@setOnClickListener
            }
            // Realiza una consulta en la colección en lugar de un documento
            db2.collection("Productos")
                .whereEqualTo("descripcion", descrip)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        binding2.etconsulta.setText("No se encontró el producto con descripción $descrip")
                    } else {
                        val datos = StringBuilder()
                        for (document in documents) {
                            datos.append("${document.id} : ${document.data}\n")
                        }
                        binding2.etconsulta.setText(datos.toString())
                    }
                }
                .addOnFailureListener {
                    binding2.etconsulta.setText("Error al consultar el producto")
                }
        }
        // --- Botón Consultar por descripción ---
        // +++ Botón Eliminar +++
        binding2.btBajar.setOnClickListener() {
            val codigo = binding2.etid.text.toString().trim().toIntOrNull()

            if (codigo == null) {
                binding2.etconsulta.setText("ERROR: ID vacía o incorrecta")
                return@setOnClickListener
            }

            db2.collection("Productos")
                .whereEqualTo("id_producto", codigo)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        binding2.etconsulta.setText("Error: No existe un producto con esa ID")
                    } else {
                        for (document in documents) {
                            db2.collection("Productos").document(document.id)
                                .delete()
                                .addOnSuccessListener {
                                    binding2.etconsulta.setText("Producto eliminado correctamente")
                                }
                                .addOnFailureListener {
                                    binding2.etconsulta.setText("Error al eliminar el producto")
                                }
                        }
                    }
                }
                .addOnFailureListener {
                    binding2.etconsulta.setText("Error al buscar el producto")
                }
        }
        // --- Botón Eliminar ---
        // +++ Botón Modificación +++
        binding2.btModificar.setOnClickListener {
            val codigo = binding2.etid.text.toString().trim().toIntOrNull()
            val descrip = binding2.etdescripcion.text.toString().trim()
            val prec = binding2.etprecio.text.toString().trim().toIntOrNull()

            if (codigo == null || (prec == null && descrip.isEmpty())) {
                binding2.etconsulta.setText("ERROR: La ID está vacía o no hay datos que actualizar")
                return@setOnClickListener
            }

            val updates = hashMapOf<String, Any>()
            if (descrip.isNotEmpty()) {
                updates["descripcion"] = descrip
            }
            if (prec != null) {
                updates["precio"] = prec
            }

            db2.collection("Productos")
                .whereEqualTo("id_producto", codigo)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        binding2.etconsulta.setText("Error: No se encontró el producto con la ID proporcionada")
                    } else {
                        for (document in documents) {
                            db2.collection("Productos").document(document.id)
                                .update(updates)
                                .addOnSuccessListener {
                                    binding2.etconsulta.setText("Producto actualizado correctamente")
                                }
                                .addOnFailureListener {
                                    binding2.etconsulta.setText("Error al actualizar el producto")
                                }
                        }
                    }
                }
                .addOnFailureListener {
                    binding2.etconsulta.setText("Error al buscar el producto")
                }
        }
        // --- Botón Modificación ---
        // +++ Botón listar all +++
        binding2.btlistar.setOnClickListener {
            var datos = " "
            db2 . collection ("Productos")
                .get()
                .addOnSuccessListener { docs2 ->
                    for (documento in docs2) {
                        datos += "${documento.id} : ${documento.data}\n"
                    }
                    binding2.etconsulta.setText(datos)
                }
                .addOnFailureListener { exception ->
                    binding2.etconsulta.setText(" error")
                }
        }
        // --- Botón listar all ---
    }
}