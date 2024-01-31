package com.qw.framework.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.qw.framework.R;
import com.qw.framework.ui.BaseFragment;

import java.util.ArrayList;

/**
 * Created by qinwei on 2017/4/21.
 */

public class SupportListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView mMainLsv;
    private ArrayList<ContainerClazz> modules = new ArrayList<>();
    private DataAdapter adapter;

    public static Fragment newInstance(ArrayList<ContainerClazz> cs) {
        SupportListFragment fragment = new SupportListFragment();
        Bundle args = new Bundle();
        args.putSerializable("cs", cs);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_support_fragment, container, false);
    }

    @Override
    public void initView(@NonNull View view) {
        mMainLsv = view.findViewById(R.id.mListView);
        adapter = new DataAdapter();
        mMainLsv.setAdapter(adapter);
        mMainLsv.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        ArrayList<ContainerClazz> cs = (ArrayList<ContainerClazz>) getArguments().getSerializable("cs");
        modules.addAll(cs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContainerClazz info = (ContainerClazz) parent.getAdapter().getItem(position);
        startActivity(ContainerFragmentActivity.getIntent(getContext(), info));
    }

    class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return modules.size();
        }

        @Override
        public Object getItem(int position) {
            return modules.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.initData(position);
            return convertView;
        }
    }

    class Holder {
        TextView mMainItemLabel;

        public Holder(View convertView) {
            mMainItemLabel = (TextView) convertView;
        }

        public void initData(int position) {
            mMainItemLabel.setText(modules.get(position).getTitle());
        }
    }
}