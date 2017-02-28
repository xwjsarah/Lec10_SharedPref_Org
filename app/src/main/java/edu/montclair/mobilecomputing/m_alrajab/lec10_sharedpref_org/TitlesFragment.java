package edu.montclair.mobilecomputing.m_alrajab.lec10_sharedpref_org;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static edu.montclair.mobilecomputing.m_alrajab.lec10_sharedpref_org.utils.Utils.getListFromSP;


public class TitlesFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private OnFragmentInteractionListener mListener;
    public TitlesFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //this part using file to retrieve information from existed files in project
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


      //store file title to arraylist
        final ArrayList<String> ArrayfileList= new ArrayList<>();

        try {
            File filedir = getContext().getFilesDir();
            File[] files= filedir.listFiles();

            for(File file:files)
                ArrayfileList.add(file.getName());

            } catch (Exception e){Log.e("Error", e.getMessage());}
           ArrayfileList.toArray(new String[ArrayfileList.size()]);




        View view= inflater.inflate(R.layout.fragment_titles, container, false);
        ListView ls=(ListView)view.findViewById(R.id.list_frg);

      /*  ls.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,
                getListFromSP(getContext(),"Title_")));*/

        ls.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,
                ArrayfileList.toArray(new String[ArrayfileList.size()])
                ));

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              //get file body from file name in arraylist
               String body="";
               try{
                   FileInputStream inputStream = getContext().openFileInput(ArrayfileList.get(i));

                   int c;
                   while ((c=inputStream.read())!=-1){

                       body+=Character.toString((char)c);
                   }

               }catch (Exception e){Log.e("Error", e.getMessage());}

                mListener.onFragmentInteraction(body);


               // mListener.onFragmentInteraction(getListFromSP(getContext(),"Body_")[i]);
            }
        });
        return view;
    }
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String uri);
    }
}
