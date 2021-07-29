package com.example.tasklist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.Adapter.ToDoAdapter;

   public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ToDoAdapter adapter;

    public RecyclerItemTouchHelper(ToDoAdapter adapter)
    {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target)
    {
        return false;
    }

       @Override
       public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
           final int position = viewHolder.getAbsoluteAdapterPosition();
           if (direction == ItemTouchHelper.LEFT)
           {
               AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
               builder.setTitle("Delete Task");
               builder.setMessage("Are you sure you want to delete this task");
               builder.setPositiveButton("Confirm",
                       new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               adapter.deleteItem(position);
                           }
                       });
               builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       adapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                   }
               });
               AlertDialog dialog = builder.create();
               dialog.show();
           }
           else {
               adapter.editItem(position);
           }

       }
       @Override
       public void  onChildDraw(Canvas c, RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,float dX,float dY,int actionState,boolean isCurrentActive) {
           super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentActive);
           Drawable icon;
           ColorDrawable background;

           View itemView = viewHolder.itemView;
           int backgroundCornerOffset = 20;

           if (dX > 0) {
               icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit_24);
               background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.design_default_color_primary_dark));

           } else {
               icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete_24);
               background = new ColorDrawable(Color.RED);
           }

           int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
           int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
           int iconBottom = iconTop + icon.getIntrinsicHeight();

           if (dX > 0) {
               int iconLeft = itemView.getLeft() + iconMargin;
               int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
               icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

               background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());


           } else if (dX < 0) {
               int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
               int iconRight = itemView.getRight() - iconMargin;
               icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

               background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());

           } else {
               background.setBounds(0, 0, 0, 0);
           }

           background.draw(c);
           icon.draw(c);
       }

   }



