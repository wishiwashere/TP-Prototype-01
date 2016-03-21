var express = require('express');
var router = express.Router();
var Jimp = require('jimp');

router.get('/:cameraImage', function(req, res, next) {
    Jimp.read(req.params.cameraImage, function(err, image){
        console.log("Image received - " + req.params.cameraImage);
    });
    res.send("Thank you :)");
});

module.exports = router;
