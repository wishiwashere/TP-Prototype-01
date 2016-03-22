var express = require('express');
var router = express.Router();

var onecolor = require('onecolor');
var Jimp = require('jimp');

var keyedImage;

Jimp.read("./girlGreenScreen.jpg", function(err, cameraImage){
    console.log("Image loaded");
    cameraImage.rgba(true);
    console.log(cameraImage.getPixelColor(1, 1));
    
    cameraImage.scan(0, 0, cameraImage.bitmap.width, cameraImage.bitmap.height, function (x, y, idx) {

        var pixelColorObject = Jimp.intToRGBA(cameraImage.getPixelColor(x, y));
        var pixelColor = "rgb(" + pixelColorObject.r + ", " + pixelColorObject.g + ", " + pixelColorObject.b + ")";
       
        var pixelHue = onecolor(pixelColor).hue() * 360;
        var pixelSaturation = onecolor(pixelColor).saturation() * 100;
        var pixelBrightness = onecolor(pixelColor).value() * 100;
        
        var hsvCol = "hsv(" + pixelHue + ", " + pixelSaturation + "%, " + pixelBrightness + "%)";
        //var hsvCol = onecolor(pixelColor).hsl();
        //console.log("H: " + pixelHue + "; S: " + pixelSaturation + "; V: " + pixelBrightness + "; A: " + pixelColorObject.a);
        //var pixelRed = onecolor(pixelColor).red() * 255;
        //var pixelGreen = onecolor(pixelColor).green() * 255;
        //var pixelBlue = onecolor(pixelColor).blue() * 225;
        
        /*
        var pixelRed = pixelColorObject.r;
        var pixelGreen = pixelColorObject.g;
        var pixelBlue = pixelColorObject.b;
        var pixelAlpha = 255;
        var newColor = Jimp.rgbaToInt(pixelRed , pixelGreen, pixelBlue, pixelAlpha);
        */
        
        if(pixelHue > 90 && pixelHue < 150){
            hsvCol = "hsv(" + pixelHue * 0.3 + ", " + pixelSaturation + "%, " + pixelBrightness + "%)";
        }
        
        var pixelRed = Math.round(onecolor(hsvCol).red() * 255);
        var pixelGreen = Math.round(onecolor(hsvCol).green() * 255);
        var pixelBlue = Math.round(onecolor(hsvCol).blue() * 255);
        var pixelAlpha = 255;
        
        if(x == 1 && y == 1){
            console.log(pixelColorObject);
            console.log(hsvCol);
            console.log("R: " + pixelRed + "; G: " + pixelGreen + "; B: " + pixelBlue + ";");
        }
        
        
        var newColor = Jimp.rgbaToInt(pixelRed, pixelGreen, pixelBlue, pixelAlpha);
 
        cameraImage.setPixelColor(newColor, x, y);
    });
    cameraImage.write("finalImage.png");
    console.log("DONE");
    
   
    //console.log(onecolor(Jimp.intToRGBA(image.getPixelColor(1, 1))).lightness());
    //console.log(Jimp.intToRGBA(image.getPixelColor(1, 1)));
});

router.get('/:message', function(req, res, next) {
    console.log(req.params.message);
    res.send("Thank you :)");
});

router.get('/', function(req, res, next) {
    /*
    fs.readFile("./girlGreenScreen.jpg", function(err, data){
        if(err) {
            console.log("Unable to load image - " + err);
        } else {
            console.log("Image loaded");
            
        }
    });
    */
    /*
    Jimp.read(req.params.cameraImage.buffer, function(err, image){
        console.log("Image received - " + req.params.cameraImage);
        image.write("testImage.png", function(err){
            if(err){
                console.log("Could not save");
            } else {
                console.log("Completed Save");
            }
        })
    });
    */
    
    
    res.download("./finalImage.png");
});

module.exports = router;
