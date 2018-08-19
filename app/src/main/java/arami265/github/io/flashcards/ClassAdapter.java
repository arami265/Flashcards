package arami265.github.io.flashcards;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by arami265 on 4/11/2017.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    private List<ClassListItem> classListItems;
    private Context context;

    dbAdapter dbhelper = new dbAdapter(context);
    displayClasses act;

    public void removeClass(int clid)
    {
        //String fields = dbhelper.getFieldNames();
        //Message.message(this, fields);

        //Intent myIntent = new Intent(this, addClass.class);
        //startActivity(myIntent);

        dbhelper.deleteClass(clid);
    }



    public ClassAdapter(List<ClassListItem> classListItems, Context context, displayClasses act) {
        this.classListItems = classListItems;
        this.context = context;
        this.act = act;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassListItem classListItem = classListItems.get(position);

        holder.classTextView.setText(classListItem.getClassName());
        holder.classIDview.setText("Class ID: " + classListItem.getClid());
    }

    @Override
    public int getItemCount() {
        return classListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView classTextView;
        public TextView classIDview;

        public ViewHolder(final View itemView) {
            super(itemView);

            classTextView = (TextView) itemView.findViewById(R.id.classTextView);
            classIDview = (TextView) itemView.findViewById(R.id.classIDview);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    Intent intent = new Intent(view.getContext(), displayDecks.class);
                    intent.putExtra("CLID", classListItems.get(getAdapterPosition()).getClid());
                    intent.putExtra("CLNAME", classListItems.get(getAdapterPosition()).getClassName());
                    act.startActivityForResult(intent,0);
                    //context.startActivity(intent);
                    //Toast.makeText(context, "About to pass the CLID " + classListItems.get(getAdapterPosition()).getClid(), Toast.LENGTH_SHORT).show();
                }
            });

            /*itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    Toast.makeText(view.getContext(), "Class removed: "+String.valueOf(classTextView.getText()), Toast.LENGTH_SHORT).show();

                    //dbhelper.deleteClass(String.valueOf(classTextView.getText()));
                    //classListItems = dbhelper.getClasses();

                    //REMOVES LONG CLICKED ITEM FROM THE VIEW ONLY!!

                    //System.out.println(getAdapterPosition());
                    Toast.makeText(view.getContext(), String.valueOf(classListItems.get(getAdapterPosition()).getClid()), Toast.LENGTH_LONG).show();
                    *//*removeClass(classListItems.get(getAdapterPosition()).getClid());
                    classListItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());*//*

                    return true;
                }
            });*/
        }
    }

}
