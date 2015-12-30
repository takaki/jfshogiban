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

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.media_as.takaki.jfshogiban.core.Koma;
import org.media_as.takaki.jfshogiban.piece.IPiece;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PieceImage {
    private static final Map<IPiece, String> FILE_NAME = new HashMap<>();
    private static final Map<IPiece, Image> SVG_MAP;

    static {
        FILE_NAME.put(Koma.SENTE_FU, "WhitePawn");
        FILE_NAME.put(Koma.SENTE_KYOSHA, "WhiteLance");
        FILE_NAME.put(Koma.SENTE_KEIMA, "WhiteKnight");
        FILE_NAME.put(Koma.SENTE_GIN, "WhiteAdvisor");
        FILE_NAME.put(Koma.SENTE_KIN, "WhiteGold");
        FILE_NAME.put(Koma.SENTE_KAKU, "WhiteBishop");
        FILE_NAME.put(Koma.SENTE_HISYA, "WhiteRook");
        FILE_NAME.put(Koma.SENTE_GYOKU, "WhiteKing");
        FILE_NAME.put(Koma.SENTE_TOKIN, "WhiteGoldPawn");
        FILE_NAME.put(Koma.SENTE_NARIKYO, "WhiteGoldLance");
        FILE_NAME.put(Koma.SENTE_NARIKEI, "WhiteGoldKnight");
        FILE_NAME.put(Koma.SENTE_NARIGIN, "WhiteGoldSilver");
        FILE_NAME.put(Koma.SENTE_UMA, "WhiteCrownedBishop");
        FILE_NAME.put(Koma.SENTE_RYU, "WhiteCrownedRook");

        FILE_NAME.put(Koma.GOTE_FU, "BlackPawn");
        FILE_NAME.put(Koma.GOTE_KYOSHA, "BlackLance");
        FILE_NAME.put(Koma.GOTE_KEIMA, "BlackKnight");
        FILE_NAME.put(Koma.GOTE_GIN, "BlackAdvisor");
        FILE_NAME.put(Koma.GOTE_KIN, "BlackGold");
        FILE_NAME.put(Koma.GOTE_KAKU, "BlackBishop");
        FILE_NAME.put(Koma.GOTE_HISYA, "BlackRook");
        FILE_NAME.put(Koma.GOTE_GYOKU, "BlackKing");
        FILE_NAME.put(Koma.GOTE_TOKIN, "BlackGoldPawn");
        FILE_NAME.put(Koma.GOTE_NARIKYO, "BlackGoldLance");
        FILE_NAME.put(Koma.GOTE_NARIKEI, "BlackGoldKnight");
        FILE_NAME.put(Koma.GOTE_NARIGIN, "BlackGoldSilver");
        FILE_NAME.put(Koma.GOTE_UMA, "BlackCrownedBishop");
        FILE_NAME.put(Koma.GOTE_RYU, "BlackCrownedRook");

        SVG_MAP = FILE_NAME.keySet().stream()
                .collect(Collectors.toMap(Function.identity(), iPiece1 -> {
                    try {
                        return mapImageView(iPiece1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }


    private static WritableImage mapImageView(final IPiece koma) throws IOException, TranscoderException {
        final BufferedImageTranscoder imageTranscoder = new BufferedImageTranscoder();

        final Path path = Paths.get("/home/takaki/tmp/xboard");
        final TranscoderInput input = new TranscoderInput(Files.newInputStream(
                path.resolve(FILE_NAME.get(koma) + ".svg")));
        imageTranscoder.transcode(input, null);
        final BufferedImage bufferedImage = imageTranscoder.getBufferedImage();
        final WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;

    }

    public static ImageView getImageView(final IPiece koma) {
        final ImageView imageView = new ImageView(SVG_MAP.get(koma));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }
    public static Image getSvgImage(final IPiece koma) {
        return SVG_MAP.get(koma);
    }
}
