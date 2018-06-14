# google-cloud-storage-java-helper

Simple Java class to create/get file from Google Cloud Storage

Google Cloud Storage Java Helper allows you to retrieve or store file to Google Cloud Storage.

## Authentication

1. Generate the Google service account credential for your Storage in JSON format and rename it to 'google_credential.json'.
2. Put the google_credential.json in your Java Project under src/main/resources.

## How to run

* Spring Boot

```bash
1. cd examples
2. run 'gradle bootRun' in terminal
3. open 'http://localhost:5000/rubetrio/home'
```

* Docker

```bash
1. cd examples
2. run 'gradle build'
3. run 'docker build -t google-cloud-storage-java-helper .'
4. run 'docker run -p 8080:8080 -v [your-local-path-to-google-credential]/google_credential.json:/credentials.json -e "GOOGLE_APPLICATION_CREDENTIALS=/credentials.json" -t google-cloud-storage-java-helper'
5. open 'http://localhost:8080/rubetrio/home'
```
