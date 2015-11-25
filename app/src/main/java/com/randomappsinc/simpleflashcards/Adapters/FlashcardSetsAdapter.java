package com.randomappsinc.simpleflashcards.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.randomappsinc.simpleflashcards.Persistence.DataObjects.FlashcardSet;
import com.randomappsinc.simpleflashcards.Persistence.DatabaseManager;
import com.randomappsinc.simpleflashcards.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 11/20/15.
 */
public class FlashcardSetsAdapter extends BaseAdapter {
    private Context context;
    private List<String> setNames;
    private TextView noSets;

    public FlashcardSetsAdapter(Context context, TextView noSets) {
        this.context = context;
        this.setNames = new ArrayList<>();
        List<FlashcardSet> sets = DatabaseManager.get().getAllFlashcardSets();
        for (FlashcardSet set : sets) {
            this.setNames.add(set.getName());
        }
        this.noSets = noSets;
        setNoContent();
    }

    public void setNoContent() {
        int viewVisibility = setNames.isEmpty() ? View.VISIBLE : View.GONE;
        noSets.setVisibility(viewVisibility);
    }

    public void addSet(String newSetName) {
        DatabaseManager.get().addSet(newSetName, setNames.size());
        setNames.add(newSetName);
        setNoContent();
        notifyDataSetChanged();
    }

    public int getCount()
    {
        return setNames.size();
    }

    public String getItem(int position)
    {
        return setNames.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public class NameViewHolder {
        @Bind(R.id.set_name) public TextView name;
        @Bind(R.id.edit_icon) public IconTextView edit;

        public NameViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(final int position, View view, ViewGroup parent) {
        NameViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.flashcard_set_cell, parent, false);
            holder = new NameViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (NameViewHolder) view.getTag();
        }

        holder.name.setText(setNames.get(position));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

            }
        });
        return view;
    }
}