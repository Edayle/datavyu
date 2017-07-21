/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.datavyu.plugins.nativeosx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.datavyu.plugins.BaseDataViewer;
import quicktime.std.movies.Track;
import quicktime.std.movies.media.Media;

import java.awt.*;
import java.io.File;


/**
 * The viewer for a quicktime video file.
 * <b>Do not move this class, this is for backward compatibility with 1.07.</b>
 */
public final class NativeOSXViewer extends BaseDataViewer {


    private long timeOfPrevSeek = 0;
    /**
     * The logger for this class.
     */
    private static Logger LOGGER = LogManager.getLogger(NativeOSXViewer.class);

    long prevSeekTime = -1;
    /**
     * The quicktime movie this viewer is displaying.
     */
    private NativeOSXPlayer movie;

    private boolean seeking = false;

    private long duration = 0;

    public NativeOSXViewer(final Frame parent, final boolean modal) {
        super(parent, modal);

        movie = null;
    }

    @Override
    protected void setPlayerVolume(final float volume) {

        if (movie == null) {
            return;
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                movie.setVolume(volume, movie.id);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDuration() {

        if(movie.getDuration(movie.id) < 0) {
            try { Thread.sleep(2000); } catch (Exception e) { e.printStackTrace(); }
            System.out.println("Error: Could not get duration. Sleeping");
        }

        if(duration == 0) {
            duration = movie.getDuration(movie.id);
        }
        return duration;
    }

    @Override
    protected void setPlayerSourceFile(final File videoFile) {

        // Ensure that the native hierarchy is set up
        this.addNotify();

        movie = new NativeOSXPlayer(videoFile);

        this.add(movie, BorderLayout.CENTER);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Make sure movie is actually loaded
                    movie.setVolume(0.7F, movie.id);
//                    movie.setRate(1.0f, movie.id);
//                    while(movie.getCurrentTime(movie.id) < 1000) {}
//                    System.out.println(getCurrentTime());
//                    movie.stop(movie.id);
//                    movie.setTime(0, movie.id);
                } catch (Exception e) {
                    // Oops! Back out
                    NativeOSXPlayer.playerCount -= 1;
                    throw e;
                }
            }
        });

//        setPlaybackSpeed(1.0f);
//        play();
//        while(movie.getCurrentTime(movie.id) < 1000) {}
//        stop();

    }

    @Override
    protected Dimension getOriginalVideoSize() {
        System.err.println(movie.id);
        return new Dimension((int) movie.getMovieWidth(movie.id), (int) movie.getMovieHeight(movie.id));
    }

    @Override
    protected float getPlayerFramesPerSecond() {
        float fps = movie.getFPS(movie.id);
        if (fps <= 1) {
            try {
                Thread.sleep(2000);
                fps = movie.getFPS(movie.id);
                if(fps > 1) {
                    return fps;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isAssumedFramesPerSecond = true;
            return 29.97f;
        }
        return movie.getFPS(movie.id);
    }

    @Override
    public void setPlaybackSpeed(final float rate) {
        super.setPlaybackSpeed(rate);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        super.play();
        System.err.println("Playing at " + getPlaybackSpeed());

        try {

            if (movie != null) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        if (movie.getRate(movie.id) != 0) {
                            movie.stop(movie.id);
                        }
                        movie.setRate(getPlaybackSpeed(), movie.id);
                    }
                });
            } else {
                System.err.println("WARNING: Movie is currently null");
            }
        } catch (Exception e) {
            LOGGER.error("Unable to play", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        super.stop();

        System.out.println("HIT STOP");
        final double time = System.currentTimeMillis();
        try {

            if (movie != null) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        System.out.println("EXECUTING STOP");
                        System.out.println(System.currentTimeMillis() - time);
                        movie.stop(movie.id);
                        System.out.println("STOPPED");
                        System.out.println(System.currentTimeMillis() - time);
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("Unable to stop", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seek(final long position) {

//        System.out.println("ASKED FOR SEEK TO " + position);
//        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
        if(System.currentTimeMillis() - timeOfPrevSeek < 35) {
//            System.out.println("skipping seek");
            return;
        }

        if (!seeking) {
            seeking = true;
            try {
                if (movie != null) {
                    prevSeekTime = position;
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            System.out.println("Seeking to " + position);
                            boolean wasPlaying = isPlaying();
                            float prevRate = getPlaybackSpeed();
                            if (isPlaying()) {
//                                System.out.println("Stopping playback");
                                movie.stop(movie.id);
                            }
                            if(prevRate >= 0 && prevRate <= 8) {
//                                System.out.println("Precise seeking!");
                                movie.setTimePrecise(position, movie.id);
                            } else if (prevRate < 0  && prevRate > -8) {
//                                System.out.println("Moderate seeking!");
                                movie.setTimeModerate(position, movie.id);
                            } else {
//                                System.out.println("Fast seeking!");
                                movie.setTime(position, movie.id);
                            }
                            if (wasPlaying) {
                                movie.setRate(prevRate, movie.id);
                            }
                            movie.repaint();
                            timeOfPrevSeek = System.currentTimeMillis();
                            seeking = false;
                        }
                    });

                }
            } catch (Exception e) {
                LOGGER.error("Unable to find", e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCurrentTime() {

        try {
            return movie.getCurrentTime(movie.id);
        } catch (Exception e) {
            LOGGER.error("Unable to get time", e);
        }

        return 0;
    }

    @Override
    protected void cleanUp() {
        //TODO
//        movie.release();
    }
}
