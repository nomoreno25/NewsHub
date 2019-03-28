package com.hailv.newshub.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hailv.newshub.R;
import com.hailv.newshub.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    Activity context;
    int resource;
    List<News> objects;
    /**
     * @param context
     * @param resource
     * @param objects
     * */
    public NewsAdapter(Activity context, int resource, List<News> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView tvTitle = (TextView) row.findViewById(R.id.tvTitle);
        TextView tvDesc = (TextView) row.findViewById(R.id.tvDesc);
        ImageView ivImg = (ImageView) row.findViewById(R.id.ivImg);
        /** Set data to row*/
        final News news = this.objects.get(position);
        tvTitle.setText(news.getTitle());
        tvDesc.setText(news.getDescription());
        Picasso.get().load(news.getUrlToImage()).into(ivImg);

        /**Set Event Onclick*/
        return row;
    }
    /** Show mesage*/
    private void showMessage(News news) {
        Toast.makeText(this.context, news.toString(),Toast.LENGTH_LONG).show();
    }
}
