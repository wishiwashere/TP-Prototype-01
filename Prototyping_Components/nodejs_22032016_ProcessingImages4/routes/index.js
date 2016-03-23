var express = require('express');
var router = express.Router();

var onecolor = require('onecolor');
var Jimp = require('jimp');

Jimp.read("./girlGreenScreen.jpg", function (err, cameraImage) {
    var keyedImage = cameraImage.clone();

    console.log("Image loaded");

    var keyingImageStarted = Date.now();

    cameraImage.scan(0, 0, cameraImage.bitmap.width, cameraImage.bitmap.height, function (x, y, idx) {

        var pixelColorObject = Jimp.intToRGBA(cameraImage.getPixelColor(x, y));
        var pixelColor = "rgb(" + pixelColorObject.r + ", " + pixelColorObject.g + ", " + pixelColorObject.b + ")";

        var pixelHue = onecolor(pixelColor).hue() * 360;
        var pixelSaturation = onecolor(pixelColor).saturation() * 100;
        var pixelBrightness = onecolor(pixelColor).value() * 100;

        var hsvCol = "hsv(" + pixelHue + ", " + pixelSaturation + "%, " + pixelBrightness + "%)";;
        var pixelAlpha = 255;
        
        // If the hue of this pixel falls anywhere within the range of green in the colour spectrum
        if (pixelHue > 60 && pixelHue < 180) {
            // If the saturation and brightness are above 30, then this is a green pixel
            if (pixelSaturation > 30 && pixelBrightness > 30) {
                if (pixelHue > 115 && pixelHue < 150) {
                    // Set this pixel in the keyedImage to be transparent (Removing the main areas of the green)
                    pixelAlpha = 0;
                } else if (pixelHue > 90 && pixelHue < 100) {
                    // If the hue of the pixel is between 90 and 100, this is not fully green, but with a tinge
                    // This seems to effect the girl's hair on the left
                    // Lowering the hue, saturation and opacity, to reduce the intensity of the colour
                    hsvCol = "hsv(" + pixelHue * 0.3 + ", " + pixelSaturation * 0.4 + "%, " + pixelBrightness + "%)";
                    pixelAlpha = 200;
                } else if (pixelHue > 155) {
                    // Increasing the hue, and reducing the saturation
                    hsvCol = "hsv(" + pixelHue * 1.2 + ", " + pixelSaturation * 0.5 + "%, " + pixelBrightness + "%)";
                } else if (pixelHue < 115) {
                    // Reducting the hue and saturation
                    hsvCol = "hsv(" + pixelHue * 0.4 + ", " + pixelSaturation * 0.5 + "%, " + pixelBrightness + "%)";
                } else {
                    if (pixelHue > 130) {
                        // This seems to be the edges around the girl
                        // Increasing the hue, reducing the saturation and displaying the pixel at half opacity
                        hsvCol = "hsv(" + pixelHue * 1.1 + ", " + pixelSaturation * 0.5 + "%, " + pixelBrightness + "%)";
                        pixelAlpha = 150;
                    } else {
                        // Set this pixel in the keyedImage to be transparent (Removing the last areas of the green)
                        pixelAlpha = 0;
                    }
                }
            } else {
                // Even though this pixel falls within the green range of the colour spectrum, it's saturation and brightness
                // are low enough that it is unlikely to be a part of the green screen, but may just be an element of the scene
                // that is picking up a glow off the green screen. Lowering the hue and saturation to remove the green tinge 
                // from this pixel.
                hsvCol = "hsv(" + pixelHue * 0.6 + ", " + pixelSaturation * 0.3 + "%, " + pixelBrightness + "%)";
            }
            
            var pixelRed = Math.round(onecolor(hsvCol).red() * 255);
            var pixelGreen = Math.round(onecolor(hsvCol).green() * 255);
            var pixelBlue = Math.round(onecolor(hsvCol).blue() * 255);

            var newColor = Jimp.rgbaToInt(pixelRed, pixelGreen, pixelBlue, pixelAlpha);

            keyedImage.setPixelColor(newColor, x, y);
        } else {
            // Since this pixel did not fall within any of the wider ranges of green in the colour spectrum,
            // we are not going to effect it's pixel colour value
        }
        
    });
    console.log("Green screen keying completed in " + ((Date.now() - keyingImageStarted) / 1000) + " seconds");

    var savingImageStarted = Date.now();
    keyedImage.write("finalImage.png", function () {
        console.log("Image saved in " + ((Date.now() - savingImageStarted) / 1000) + " seconds");
    });
});

router.get('/:message', function (req, res, next) {
    console.log(req.params.message);
    res.send("Thank you :)");
});

router.get('/', function (req, res, next) {
    res.download("./finalImage.png");
});

module.exports = router;
