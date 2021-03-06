package ao.dicionariodetermosinformaticos.romavicdosanjoskc.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ao.dicionariodetermosinformaticos.romavicdosanjoskc.R
import ao.dicionariodetermosinformaticos.romavicdosanjoskc.models.DictionaryModel
import ao.dicionariodetermosinformaticos.romavicdosanjoskc.presenter.DictionaryPresenter
import ao.dicionariodetermosinformaticos.romavicdosanjoskc.presenter.utils.AppUtil.customToast
import ao.dicionariodetermosinformaticos.romavicdosanjoskc.presenter.utils.AppUtil.shareInformation
import ao.dicionariodetermosinformaticos.romavicdosanjoskc.view.views.DictionaryView
import java.util.*

class ReceiveDictionaryWordActivity : AppCompatActivity(), DictionaryView {

    private var receiveTitle: TextView? = null
    private var receiveMeaning: TextView? = null
    private var receiveDataTitle: String? = ""
    private var receiveDataMeaning: String? = ""

    private var presenter: DictionaryPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_dictionary_word)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        receiveTitle = findViewById(R.id.receiveTitle)
        receiveMeaning = findViewById(R.id.receiveMeaning)

        presenter = DictionaryPresenter(this)
        presenter?.getDictionary()

        val intent = intent
        receiveDataTitle = intent.getStringExtra("title")
        receiveDataMeaning = intent.getStringExtra("meaning")
        if (Objects.requireNonNull(receiveDataMeaning)!!.isEmpty() || Objects.requireNonNull(receiveDataTitle)!!.isEmpty()) {
            finish()
        } else {
            receiveTitle?.text = receiveDataTitle
            receiveMeaning?.text = receiveDataMeaning
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> {
                shareInformation(this, receiveDataTitle!!, receiveDataMeaning)
            }
            R.id.menu_save -> {
                customToast(this, "Save")
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onDictionarySuccess(wordModelList: MutableList<DictionaryModel>) {
        Log.i("info", wordModelList.size.toString())
    }

    override fun onDictionaryError(messageError: String) {
        Log.i("info", messageError)
    }
}