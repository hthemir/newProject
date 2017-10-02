package com.example.pessoal.newproject.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pessoal.newproject.R;
import com.example.pessoal.newproject.activities.ActivityHome;
import com.example.pessoal.newproject.fragments.FragmentNewNote;
import com.example.pessoal.newproject.fragments.FragmentNotes;
import com.example.pessoal.newproject.model.Note;

import java.util.List;

/**
 * Created by ZUP on 29/09/2017.
 */

public class AdapterNoteRecyclerView extends RecyclerView.Adapter<AdapterNoteRecyclerView.ThisViewHolder> {

    private List<Note> mList;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private ActivityHome mActivity;
    private FragmentNotes mFragment;

    public AdapterNoteRecyclerView(List<Note> list, Context context, FragmentNotes fragmentNotes) {
        mList = list;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFragment = fragmentNotes;
    }

    /*private void replaceFragment(Fragment fragment){
        if (context instanceof ActivityHome) {
            mActivity = (ActivityHome) context;
        }

        mActivity.replaceFragment(fragment, "tag", false);
    }*/

    private void editOrRemoveNote(Note note, boolean isRemove) {
        //isRemove ? mFragment.removeNote(note) : mFragment.editNote(note);
        if(isRemove) {
            mFragment.removeNote(note);
            notifyDataSetChanged();
        } else
            mFragment.editNote(note);
    }

    @Override
    public ThisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_note_item, parent, false);
        ThisViewHolder mViewHolder = new ThisViewHolder(view);
        context = parent.getContext();
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ThisViewHolder holder, final int position) {
        holder.tvListNoteTitle.setText(mList.get(position).getTitle());
        holder.tvListNoteContent.setText(mList.get(position).getContent());
        holder.ivEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrRemoveNote(mList.get(position), false);
            }
        });
        holder.ivDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrRemoveNote(mList.get(position), true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ThisViewHolder extends RecyclerView.ViewHolder {

        public TextView tvListNoteTitle;
        public ImageView ivEditNote;
        public ImageView ivDeleteNote;
        public TextView tvListNoteContent;

        public ThisViewHolder(View itemView) {
            super(itemView);

            tvListNoteTitle = (TextView) itemView.findViewById(R.id.tv_list_note_title);
            ivEditNote = (ImageView) itemView.findViewById(R.id.iv_edit);
            ivDeleteNote = (ImageView) itemView.findViewById(R.id.iv_delete);
            tvListNoteContent = (TextView) itemView.findViewById(R.id.tv_list_note_content);
        }
    }
}

