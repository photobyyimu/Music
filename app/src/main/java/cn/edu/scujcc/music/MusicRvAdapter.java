package cn.edu.scujcc.music;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MusicRvAdapter extends RecyclerView.Adapter<MusicRvAdapter.MusicRowHolder> {

    private MusicLab lab = MusicLab.getInstance();
    private MusicClickListener listener;
    private Context context;

    public MusicRvAdapter(Context context, MusicClickListener lis) {
        this.context = context;
        this.listener = lis;
    }

    /**
     * 当需要新的一行时，此方法负责创建这一行对应的对象，即MusicRowHolder对象。
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MusicRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_row, parent, false);
        MusicRowHolder holder = new MusicRowHolder(rowView);
        return holder;
    }

    /**
     * 用于确定列表总共有几行(即多少个MusicRowHolder对象）
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return lab.getSize();
    }

    /**
     * 用于确定每一行的内容是什么，即填充行中各个视图的内容。
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MusicRowHolder holder, int position) {
        Music c = lab.getMusic(position);
        holder.bind(c);
    }

    //自定义新接口
    public interface MusicClickListener {
        public void onMusicClick(int position);
    }

    /**
     * 单行布局对应的Java控制类
     */
    public class MusicRowHolder extends RecyclerView.ViewHolder {
        private TextView songtitle;  //频道标题
        private TextView qingxidu;  //频道清晰度
        private ImageView image;  //频道封面图
        private TextView singer;//歌手
        public MusicRowHolder(@NonNull View row) {
            super(row);
            this.songtitle = row.findViewById(R.id.song_title);
            this.qingxidu = row.findViewById(R.id.qingxidu);
            this.image = row.findViewById(R.id.cover);
            this.singer = row.findViewById(R.id.singer);
            row.setOnClickListener((v) -> {
                int position = getLayoutPosition();
                Log.d("lala", position + "行被点击了！");
                listener.onMusicClick(position);
            });
        }

        /**
         * 自定义方法，用于向内部的title提供数据
         */
        public void bind(Music c) {
            this.songtitle.setText(c.getSongtitle());
            this.qingxidu.setText(c.getQingxidu());
            //弃用本地图片的方式，改为从网络加载图片
//            this.cover.setImageResource(c.getCover());
            Log.d("lala", c.getSongtitle() + "：准备从网络加载封面：" + c.getImage());
            Glide.with(context)
                    .load(c.getImage())
                    .into(this.image);
        }
    }
}