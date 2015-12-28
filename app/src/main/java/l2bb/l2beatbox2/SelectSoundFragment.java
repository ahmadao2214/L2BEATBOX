package l2bb.l2beatbox2;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by OZ on 12/24/2015.
 */
public class SelectSoundFragment extends ListFragment{

    private static final String ARG_SECTION_NUMBER = "section_number";

    VisualizerView mVisualizerView;
    MediaPlayer mp;

    public SelectSoundFragment(){

    }

    public static Fragment newInstance(int sectionNumber){
        SelectSoundFragment fragment = new SelectSoundFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private void addLineRenderer(){
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(7f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(getResources().getColor(R.color.colorPrimary));

        LineRenderer lineRenderer = new LineRenderer(linePaint);

        mVisualizerView.addRenderer(lineRenderer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_select_sound, container, false);

        setListAdapter(new BeatAdapter());

        mp = new MediaPlayer();

        mVisualizerView = (VisualizerView) rootView.findViewById(R.id.visualizerView);
        addLineRenderer();

        mVisualizerView.link(mp);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        playRecording(v, BEATs[position].getSoundId());
    }

    private void playRecording(View v, int SoundId) {

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();
                mVisualizerView.setEnabled(false);
            }
        });

        mp = MediaPlayer.create(v.getContext(), SoundId);

        mVisualizerView.link(mp);
        mp.start();

    }

        private static final Beat[] BEATs = {
                new Beat("Classic Kick",
                        Beat.Type.Kick,
                        "Easy",
                        R.raw.kick),

                new Beat("808 Kick",
                        Beat.Type.Kick,
                        "Easy",
                        R.raw.eightoeightkick),

                new Beat("Fast Rolls Kick",
                        Beat.Type.Kick,
                        "Hard",
                        R.raw.fastrollskick),

                new Beat("Dry Kick",
                        Beat.Type.Kick,
                        "Medium",
                        R.raw.drykick),

                new Beat("Fast HiHat",
                        Beat.Type.HiHat,
                        "Hard",
                        R.raw.fasthihat),

                new Beat("Open and Closed HiHat",
                        Beat.Type.HiHat,
                        "Easy",
                        R.raw.openclosedhihat),

                new Beat("Classic Snare",
                        Beat.Type.Snare,
                        "Medium",
                        R.raw.classicsnare),

                new Beat("Rimshot Snare",
                        Beat.Type.Snare,
                        "Medium",
                        R.raw.rimshotsnare),

                new Beat("Inward Snare",
                        Beat.Type.Snare,
                        "Hard",
                        R.raw.inwardsnare),

                new Beat("808 Snare",
                        Beat.Type.Snare,
                        "Easy",
                        R.raw.eightoeightsnare),

                new Beat("Abra Scratch",
                        Beat.Type.Scratch,
                        "Medium",
                        R.raw.abrascratch),

                new Beat("Electro Scratch",
                        Beat.Type.Scratch,
                        "Easy",
                        R.raw.electroscratch),

                new Beat("Whistle Scratch",
                        Beat.Type.Scratch,
                        "Medium",
                        R.raw.whistlescratch),

                new Beat("Throat Scratch",
                        Beat.Type.Scratch,
                        "Hard",
                        R.raw.throatscratch),

                new Beat("Vocal Scratch",
                        Beat.Type.Scratch,
                        "Medium",
                        R.raw.vocalscratch),

        };

        class BeatAdapter extends BaseAdapter {
            private LayoutInflater inflater;

            @Override
            public int getCount() {
                return BEATs.length;
            }

            @Override
            public Object getItem(int i) {
                return BEATs[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = convertView;

                if (convertView == null) {
                    if (inflater == null)
                        inflater = (LayoutInflater) SelectSoundFragment.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(R.layout.beat_list, parent, false);
                }

                ImageView icon = (ImageView) row.findViewById(R.id.image);
                TextView name = (TextView) row.findViewById(R.id.text1);
                TextView description = (TextView) row.findViewById(R.id.text2);

                Beat beat = BEATs[position];
                name.setText(beat.getName());
                description.setText(beat.getShortDescription());
                icon.setImageResource(Beat.getIconResource(beat.getType()));

                return row;
            }
        }
    }
