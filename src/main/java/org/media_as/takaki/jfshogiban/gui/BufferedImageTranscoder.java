/*
 * jfshogiban: GUI Shogi playing board
 * Copyright (C) 2015 TANIGUCHI Takaki <takaki@asis.media-as.org>
 *
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

package org.media_as.takaki.jfshogiban.gui;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.image.BufferedImage;

public final class BufferedImageTranscoder extends ImageTranscoder {

    private BufferedImage img = null;

    @Override
    public BufferedImage createImage(final int width, final int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void writeImage(final BufferedImage img,
                           final TranscoderOutput output) throws TranscoderException {
        this.img = img;
    }

    public BufferedImage getBufferedImage() {
        return img;
    }
}
