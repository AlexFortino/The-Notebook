import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.*
import com.example.android.navigation.databinding.ReciptBinding
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.core.content.FileProvider
import android.graphics.Bitmap
import android.provider.MediaStore.Images
//import javax.swing.UIManager.put
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


/**
 * @property safeArgs is the bundle from the menu fragment containing the sandwich data
 * @property listOfExtras is a list containing bindings for all of the topping text views, both the text, and their respective costs(50 cents each)
 * @param sName is the name taken from the bundle for the name of the sandwhich
 * @param sBCost is the base cost of the sandwhich taken from the bundle
 * @param sTCost is the final cost of the sandwich pre-tax
 * @param bType is the type of bread chosen
 * @param toppings is a list of toppings selected
 *
 *
 */


class ReciptFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: ReciptBinding = DataBindingUtil.inflate(
                inflater, R.layout.recipt, container, false)
        binding.doneButton.setOnClickListener { v: View ->
            v.findNavController().navigate(ReciptFragmentDirections.actionReciptFragmentToTitleFragment())
        }


        //onCreateOptionsMenu(R.menu.winner_menu, inflater)
        var listOfExtras = listOf<TextView>( binding.extra1, binding.extra2, binding.extra3, binding.extra4, binding.toppingCost1, binding.toppingCost2, binding.toppingCost3, binding.toppingCost4)

        arguments?.let {
            val safeArgs = ReciptFragmentArgs.fromBundle(it)
            val sName = safeArgs.sandParceble.name;
            val sBCost = safeArgs.sandParceble.basePrice;
            val sTCost = safeArgs.sandParceble.totalPrice;
            val bType = safeArgs.sandParceble.bread;
            val toppings = safeArgs.sandParceble.toppings;
            //Toast.makeText(context, sName, Toast.LENGTH_SHORT).show();
            binding.sandwichText.text = sName;
            binding.sandwichPrice.text = String.format("$%.2f", sBCost)
            binding.sandwichPrice2.text = String.format("$%.2f", sTCost)
            binding.sandwichPrice3.text = String.format("$%.2f", sTCost*1.1)
            binding.taxCost.text = String.format("$%.2f", sTCost*0.1)

            for(i in 0..3){
                if(toppings[i].isChecked){
                    listOfExtras[i].text = toppings[i].text;
                }else{
                    listOfExtras[i].text=" "
                    listOfExtras[i+4].text = " "
                }
            }
        }
        setHasOptionsMenu(true)

        return binding.root
        // Inflate the layout for this fragment
    }

    /**
     * Sends an intent containing a message to a social service installed on your device of your choice.
     */
    private fun getShareIntent() /*: Intent*/{
        //dispatchTakePictureIntent()
        selectImageInAlbum()
    }

    /**
     * @property intent is the first intent used to select the image
     */
    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
    }
    companion object {
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1

    }


    val REQUEST_IMAGE_CAPTURE = 1
//
//    private fun dispatchTakePictureIntent() {
//        if (ContextCompat.checkSelfPermission(getContext(),
//                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            //do your stuff
//        }else {
//            ActivityCompat.requestPermissions((Activity) getContext(),
//                    new String[]{Manifest.permission.CAMERA},
//                    MY_PERMISSIONS_REQUEST_CAMERA);
//        }

   //
    //    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile())
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

    //}


    /**
     *@property shareIntent is an intent that contains both a Txt based message and an image and sends it to an avaiable provider.
     */
    fun shareTheImage( data: Intent?){
        val shareIntent = Intent()

        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, " ALEX's SUBS!!! ")
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, data?.data)

        startActivity(Intent.createChooser(shareIntent, "Share images..."))
    }

    /**
     * Is automatically called after the activityForResult is completed.
     * Passes the data to the actual share intent.
     * @param data is the intent containing the picture, which is passed on to the shareTheImage Function
      */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Toast.makeText(context, "I HAVE AN IMAGE", Toast.LENGTH_LONG)
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM){
            //Toast.makeText(context, "I HAVE AN IMAGE", Toast.LENGTH_LONG)
            //sImageData = data.data
            //setImageURI(data?.data)
            shareTheImage(data)
        }

    }


    private fun shareSuccess() {
        //startActivity(getShareIntent())
    }

    /**
     *Control for the menu item containing the share button, Hides the share button if not possible
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)
        // check if the activity resolves
        //if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
         //   menu?.findItem(R.id.share)?.setVisible(false)
        //}

    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.winner_menu, menu)
//        return true
//    }

    /**
     * Actually Calls the Share success
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.share -> getShareIntent()
            //R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}