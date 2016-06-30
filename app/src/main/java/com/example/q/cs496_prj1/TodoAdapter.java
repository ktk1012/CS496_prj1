package com.example.q.cs496_prj1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by q on 2016-06-30.
 */
public class TodoAdapter extends RecyclerView.Adapter <TodoAdapter.ViewHolder> {

    private Context context;
    ArrayList<Todo> data = null;
    int item_layout;

    public TodoAdapter(Context c, int l, ArrayList<Todo> d) {
        this.context = c;
        this.item_layout = l;
        this.data = d;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_card, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Todo todo = data.get(position);
        holder.mWorkView.setText(todo.get_work());
        holder.mDueView.setText(todo.get_due_date());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
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
