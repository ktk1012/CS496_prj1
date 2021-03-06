package com.example.q.cs496_prj1;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by q on 2016-06-30.
 */
public class TodoAdapter extends RecyclerView.Adapter <TodoAdapter.ViewHolder> {

    private Context context;
    ArrayList<Todo> data_not_completed = null;
    ArrayList<Todo> data_all = null;
    ArrayList<Todo> data_current = null;
    boolean checked;
    int item_layout;

    public TodoAdapter(Context c, int l, ArrayList<Todo> d_1, ArrayList<Todo> d_2, boolean checked) {
        this.context = c;
        this.item_layout = l;
        this.data_not_completed = d_1;
        this.data_all = d_2;
        this.checked = checked;
        if (checked) {
            data_current = data_all;
        } else {
            data_current = data_not_completed;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_card, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Todo todo;

        todo = data_current.get(position);

        holder.mWorkView.setText(todo.get_work());
        holder.mDueView.setText(todo.get_due_date());
        holder.mCompleteCheck.setChecked(todo.get_is_complete());
        holder.mCompleteCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if (todo.get_is_complete()) {
                    data_not_completed.add(todo);
                } else {
                    data_not_completed.remove(todo);
                }
                todo.set_is_complete(!todo.get_is_complete());
                if (!checked) {
                    notifyItemRemoved(position);
                }
            }
        });
    }

    public void ChnageList(boolean flag) {
        if (flag) {
            data_current = data_all;
        } else {
            data_current = data_not_completed;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.data_current.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mWorkView;
        public final TextView mDueView;
        public final CheckBox mCompleteCheck;

        public ViewHolder(View itemView) {
            super(itemView);

            /* Initialize view items */
            mView = itemView;
            mWorkView = (TextView) itemView.findViewById(R.id.todo_name);
            mDueView = (TextView) itemView.findViewById(R.id.todo_due);
            mCompleteCheck = (CheckBox) itemView.findViewById(R.id.todo_complete);
        }
    }
}
