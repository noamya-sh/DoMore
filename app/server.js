var { initializeApp } = require('C:\\Users\\Public\\node_modules\\@firebase\\app');
var { getFirestore, collection, getDoc, doc } = require('C:\\Users\\Public\\node_modules\\@firebase\\firestore\\lite');
// const { getAuth, onAuthStateChanged, signInWithEmailAndPassword} = require("firebase/auth");


const adminApp = initializeApp({
  apiKey: "AIzaSyAXb116a4hvsGmvE4T9VsEJgky1NYfcHs4",
  authDomain: "kindness-f2638.firebaseapp.com",
  databaseURL: "https://kindness-f2638-default-rtdb.firebaseio.com",
  projectId: "kindness-f2638",
  storageBucket: "kindness-f2638.appspot.com",
  messagingSenderId: "850819678813",
  appId: "1:850819678813:web:b042b58047c2b365d443b4",
  measurementId: "G-7X6FVM59L1"
});

// let auth = getAuth(adminApp)
const db = getFirestore(adminApp);
async function getVolunteer(uid) {
  console.log(uid)
  const volunteersDoc = doc(collection(db, 'volunteers'), uid.ID);
  console.log(volunteersDoc.path)
  const volunteerSnapshot = await getDoc(volunteersDoc);
  return volunteerSnapshot.exists();
}

const express = require('C:\\Users\\Public\\node_modules\\express');
var bodyParser = require('C:\\Users\\Public\\node_modules\\body-parser');
const app = express();

app.use(bodyParser.urlencoded({
  extended: true
}));

app.use(bodyParser.urlencoded({
  extended: true
}));
app.use(express.json())

app.get('/', (req, res) => {
  res.send('GET request to the homepage')
})
app.post('/', async (req, res) => {
  var uid=req.body;
  console.log(uid);
  var bool = await getVolunteer(uid)
  res.status(200).json({"type": `${bool}`});
    
});
app.listen(8080);

