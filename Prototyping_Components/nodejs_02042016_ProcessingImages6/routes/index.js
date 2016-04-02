var express = require('express');
var router = express.Router();
var path = require("path");
var RemoveGreenScreen = require("../custom_modules/RemoveGreenScreen");

router.get('/:message', function (req, res, next) {
    console.log(req.params.message);
    res.send("Thank you :)");
});

router.post("/", function(req, res, next){
    console.log("Post request received from " + req.body.name);
    res.sendFile(path.join(__dirname, "../images/1459603100455_girlGreenScreen.jpg"));
});

router.get('/', function (req, res, next) {
    // Increasing the default timeout to 15 seconds, as the greenscreening can take alot of time
    // and I don't want the request to timeout while it is processing
    req.setTimeout(15000, function (err) {
        console.log("Server timed out " + err);
    });
    
    // Calling the HD version of removing the green screen (can switch to SD by changing the 
    // method to SD). HD Takes approximatley 8 seconds to process, while SD take 5 seconds
    RemoveGreenScreen.HD("./girlGreenScreen.jpg");
    
    // Sending the resulting image back to the user in the response
    res.download("./KeyedImage-HD.png");
});

module.exports = router;
