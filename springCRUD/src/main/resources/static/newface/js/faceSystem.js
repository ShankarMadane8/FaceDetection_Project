


function startCamera(fileName){

  console.log("whic file comare: "+fileName)
  const video = document.getElementById("video");

    

  Promise.all([
    faceapi.nets.ssdMobilenetv1.loadFromUri("/newface/models"),
    faceapi.nets.faceRecognitionNet.loadFromUri("/newface/models"),
    faceapi.nets.faceLandmark68Net.loadFromUri("/newface/models"),
  ]).then(startWebcam);
  
  function startWebcam() {
    navigator.mediaDevices
      .getUserMedia({
        video: true,
        audio: false,
      })
      .then((stream) => {
        video.srcObject = stream;
      })
      .catch((error) => {
        console.error(error);
      });
  }



  function getLabeledFaceDescriptions() {
      
      const labels = ['ross','rachel','chandler', 'monica', 'phoebe', 'joey',]
      labels.push(fileName)



      return Promise.all(
        labels.map(async (label) => {
          const descriptions = [];

          for (let i = 1; i <= 5; i++) {

          

              const imgUrl = `newface/images/${label}.jpg`
              const img = await faceapi.fetchImage(imgUrl)

            const detections = await faceapi
              .detectSingleFace(img)
              .withFaceLandmarks()
              .withFaceDescriptor();
            
            descriptions.push(detections.descriptor);
           
          }

          
          return new faceapi.LabeledFaceDescriptors(label, descriptions);
        })
      );
    }

    
let UserMovecount=0;

video.addEventListener("play", async () => {
  const labeledFaceDescriptors = await getLabeledFaceDescriptions();
  const faceMatcher = new faceapi.FaceMatcher(labeledFaceDescriptors);

  console.log(faceapi)

  const canvas = faceapi.createCanvasFromMedia(video);
  document.body.append(canvas);

  const displaySize = { width: video.width, height: video.height };
  faceapi.matchDimensions(canvas, displaySize);

  setInterval(async () => {
    const detections = await faceapi
      .detectAllFaces(video)
      .withFaceLandmarks()
      .withFaceDescriptors();

    const resizedDetections = faceapi.resizeResults(detections, displaySize);

    canvas.getContext("2d").clearRect(0, 0, canvas.width, canvas.height);


    console.log(detections.length)

    if(detections.length===0){
      UserMovecount++
    }
    else if(detections.length>1){
      alert("Multiple faces detected in the camera.");
    }
  
    if(UserMovecount===5){
      alert("your face is not detecte in Camera.");
      UserMovecount=0
    }
    console.log("UserMovecount="+UserMovecount)




 
    const results = resizedDetections.map((d) => {
      return faceMatcher.findBestMatch(d.descriptor);

    });



    
  


  

    results.forEach((result, i) => {
      const box = resizedDetections[i].detection.box;
      const drawBox = new faceapi.draw.DrawBox(box, {
        label: result,

      });
      
      console.log(drawBox.options.label._label)


      if(drawBox.options.label._label!=fileName){
         alert("user face not match")
        console.log("user face not match")
      }


      drawBox.draw(canvas);
      
    });


  }, 100);
});


      console.log("star work")
    }