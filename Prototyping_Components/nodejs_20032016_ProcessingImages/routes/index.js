var express = require('express');
var router = express.Router();

var Processing = require('processing-js');

var sketch = new Processing.Sketch();

// Assuming all requests will contain a parametre in the form of an image
router.get('/:cameraImage', function(req, res, next) {
    // Storing the PImage passed to the request in a local variable
    var cameraImage = req.params.cameraImage;
    
    // Code to load the pixel array of the cameraImage
    // Code to loop through the pixels of the cameraImage to remove the green screen
    // Code to close the pixel array of the cameraImage
    
    // Passing the image back in the HTTP response, so that it can be used by the app
    res.send(cameraImage);
});

module.exports = router;
