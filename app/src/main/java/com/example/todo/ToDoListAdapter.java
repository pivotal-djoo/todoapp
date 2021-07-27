package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.models.ToDo;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private Callback callback;
    private List<ToDo> localDataSet;

    interface Callback {
        void deleteTapped(ToDo todo);
        void checkboxTapped(ToDo todo, boolean checked);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView deleteImageView;
        private final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
            deleteImageView = view.findViewById(R.id.delete_image);
            checkBox = view.findViewById(R.id.checkbox);
        }
        public TextView getTextView() {
            return textView;
        }
        public ImageView getDeleteImageView() {
            return deleteImageView;
        }
        public CheckBox getCheckBox() {
            return checkBox;
        }
    }

    public ToDoListAdapter(Callback callback) {
        this.callback = callback;
    }

    public void setData(List<ToDo> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.todo_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ToDo toDo = localDataSet.get(position);
        viewHolder.getTextView().setText(toDo.getText());
        viewHolder.getDeleteImageView()
                .setOnClickListener(v -> callback.deleteTapped(toDo));
        viewHolder.getCheckBox().setChecked(toDo.isChecked());
        viewHolder.getCheckBox()
                .setOnCheckedChangeListener((buttonView, isChecked) -> callback.checkboxTapped(toDo, isChecked));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}