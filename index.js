const express = require("express");
const cors = require("cors");
const multer = require("multer");
const serveIndex = require("serve-index");
const { default: axios } = require("axios");
const bodyParser = require("body-parser");
app = express();
app.use(bodyParser.json());
app.use(bodyParser({ extended: true }));
app.use(cors({ origin: "*" }));
app.use("/Books", serveIndex("./Books"));
const DB_URL = "http://localhost:3000";
app.use("/Books", express.static("./Books"));
const id =
  new Date().getTime() +
  new Date().getMinutes() +
  new Date().getHours() +
  new Date().getDate() +
  new Date().getMonth() +
  new Date().getFullYear();
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, "Books/");
  },
  filename: function (req, file, cb) {
    cb(null, id + "---" + file.originalname);
  },
});
const upload = multer({
  storage: storage,
  fileFilter: function (req, file, cb) {
    if (
      file.mimetype == "application/pdf" &&
      req.body.name !== "" &&
      req.body.subject !== ""
    )
      cb(null, true);
    else cb(null, false);
  },
});
const PORT = 8000 || 3000;

app.post("/api/register", async (req, res) => {
  const { name, password } = req.body;
  console.log(req.body);
  if (name && password) {
    await axios.post(DB_URL + "/users", { name: name, password: password });
    res.json({ status: "Success" });
  }
  res.json({});
});

app.post("/api/auth", async (req, res) => {

  const body = req.body;
  if (body.name && body.password) {
    const users = (await axios.get(DB_URL + "/users")).data;
    for (user of users) {
      if(user.name === body.name && body.password === user.password)
        res.json({"auth" : true})
    }
  }
  res.json({"auth" : false});
});

app.get("/", (req, res) => {
  res.json({ request: "GET" });
});
app.get("/api/upload", (req, res) => {
  res.sendFile("Books/");
});

app.get("/api/books", async (req, res) => {
  const books = await axios.get(DB_URL + "/books");
  res.json(books.data);
});

app.get("/api/subject", async (req, res) => {
  const subject = await axios.get(DB_URL + "/subject");
  res.json(subject.data);
});

app.post("/api/update/subject", async (req, res) => {
  const { name } = req.body;
  try {
    if (name) await axios.post(DB_URL + "/subject", req.body);
    else res.json({ status: "Invalid" });
  } catch (ex) {
    console.log(ex);
  }
  res.json();
});

app.post("/api/upload", upload.single("book"), (req, res) => {
  try {
    console.log(req);
    if (req.file && req.body.name && req.body.subject) {
      axios.post(DB_URL + "/books", {
        bookname: req.body.name,
        filename: req.file.filename,
        subject: req.body.subject,
        description: req.body.description || "None",
        location: req.file.path,
      });
      console.log(req.body);
      res.status(200).json({ status: "Success", file: req.file });
    } else res.status(404).json({ status: "Error" });
  } catch (ex) {
    console.log(ex);
    res.json({});
  }
});

app.listen(PORT, () => {
  console.log("PORT : ", PORT, " is running");
});
