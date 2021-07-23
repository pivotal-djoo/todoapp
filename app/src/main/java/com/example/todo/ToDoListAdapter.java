package com.example.todo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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
            textView = view.findViewById(R.id.textView);
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
        final ToDo toDo = localDataSet.get(position);
        viewHolder.getTextView().setText(toDo.getText());
        viewHolder.getDeleteImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.deleteTapped(toDo);
            }
        });
        viewHolder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                callback.checkboxTapped(toDo, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}