package com.heet.objectdetection;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;
import com.microsoft.projectoxford.face.contract.Person;
import com.microsoft.projectoxford.face.rest.ClientException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PersonRecognition {

    private PersonGroup personGroup;

    private final String endpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0";
    private final String subscriptionKey = "6e8f2e96be4d45719f8568f8298476b4";
    private final FaceServiceClient faceServiceClient = new FaceServiceRestClient(endpoint, subscriptionKey);
    private String personDetails;
    public PersonGroup getPersonGroup() {
        return personGroup;
    }

    public void setPersonGroup(PersonGroup personGroup) {
        this.personGroup = personGroup;
    }

    public void processImage (Bitmap imageBitmap, final TextToSpeech textToSpeech) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        final String TAG = "getFaces";

        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";
                    String description;

                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                    // returnFaceAttributes:
                                    new FaceServiceClient.FaceAttributeType[] {
                                            FaceServiceClient.FaceAttributeType.Age,
                                            FaceServiceClient.FaceAttributeType.Gender,
                                            FaceServiceClient.FaceAttributeType.Emotion,
                                            FaceServiceClient.FaceAttributeType.Smile,
                                            FaceServiceClient.FaceAttributeType.Glasses
                                    }

                            );
                            if (result == null){
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format(
                                    "Detection Finished. %d face(s) detected",
                                    result.length));
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        Log.d(TAG, progress[0]);
                    }
                    @Override
                    protected void onPostExecute(Face[] result) {

                        //TODO: update face frames
                        Log.d(TAG, "Completed");

                        if(!exceptionMessage.equals("")){
                            Log.d(TAG, exceptionMessage);
                        }
                        if (result == null) return;

                        Log.d("PERSON_DEATILS" , result.length + "");

                        for (Face face: result) {

                            Log.d("PERSON_DEATILS" , face.faceId + "");
                            Log.d("PERSON_DEATILS" , face.faceAttributes.age + "");
                            Log.d("PERSON_DEATILS" , face.faceAttributes.gender + "");
                            Log.d("HAPPY" , face.faceAttributes.emotion.happiness + "");
                            Log.d("ANGRY" , face.faceAttributes.emotion.anger + "");
                            Log.d("DISGUST" , face.faceAttributes.emotion.disgust + "");
                            Log.d("NEUTRAL" , face.faceAttributes.emotion.neutral + "");
                            Log.d("FEAR" , face.faceAttributes.emotion.fear + "");
                            Log.d("SADNESS" , face.faceAttributes.emotion.sadness + "");
                            Log.d("SURPRISE" , face.faceAttributes.emotion.surprise + "");
                            Log.d("SMILE" , face.faceAttributes.smile + "");
                            Log.d("GLASSES" , face.faceAttributes.glasses + "");

                            String res = "The person is a " + face.faceAttributes.gender + " approximately "
                                    + (int)face.faceAttributes.age + " years old ";

                            if (!face.faceAttributes.glasses.toString().equals("NoGlasses")) {
                                res += "and wears glasses";
                            }

                            String gender = face.faceAttributes.gender.equals("male") ? "He" : "She";

                            String exp = "";

                            if (face.faceAttributes.emotion.happiness == 1.0) {
                                exp += "happy, ";
                            }
                            if (face.faceAttributes.emotion.sadness == 1.0) {
                                exp += "sad, ";
                            }
                            if (face.faceAttributes.emotion.anger == 1.0) {
                                exp += "angry, ";
                            }
                            if (face.faceAttributes.emotion.surprise == 1.0) {
                                exp += "surprised, ";
                            }
                            if (face.faceAttributes.emotion.disgust == 1.0) {
                                exp += "disgusted, ";
                            }
                            if (face.faceAttributes.emotion.fear == 1.0) {
                                exp += "scared, ";
                            }
                            if (face.faceAttributes.emotion.neutral == 1.0) {
                                exp += "netural, ";
                            }

                            if (exp.length() != 0) {
                                res += gender + " looks " + exp;

                            }

                            Log.d("PERSON_DEATILS" , res + "");
                            description = res;
                            setStringValue(res, textToSpeech);
                        }
                    }
                };

        detectTask.execute(inputStream);
    }

    private void setStringValue(String str, TextToSpeech textToSpeech){
        textToSpeech.speak(str, TextToSpeech.QUEUE_ADD, null);
    }



}
