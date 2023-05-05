package com.crud.controller;



import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Mycontroller {
	
	@GetMapping("/start")
    public  String getvideo(Model model){
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            //Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
           // System.out.println("mat = " + mat.dump());
          
            
            CascadeClassifier faceDetector = new CascadeClassifier(
            		
            		);
            Mat frame = new Mat();
             VideoCapture capture = new VideoCapture(0);
              System.out.println("a");
             //frame = new Mat();
             new Thread(() -> {
                 while (true) {
                     capture.read(frame);
                     MatOfRect faceDetections = new MatOfRect();
                     faceDetector.detectMultiScale(frame, faceDetections);
                     for (Rect rect : faceDetections.toArray()) {
                         Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
                     }
                 }
             }).start();
            System.out.println("dd");
            
            model.addAttribute("frame", frame);
            
			return "home";
    }
}

