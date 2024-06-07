const express = require("express");
const cors = require("cors");
const multer = require("multer");
const serveIndex = require("serve-index");
const { default: axios } = require("axios");
app = express();
app.use(cors({ origin: "*" }));
app.use("/Books", serveIndex("./Books"));
const DB_URL = "http://localhost:3000/books";
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

app.get("/", (req, res) => {
  res.json({ request: "GET" });
});
app.get("/api/upload", (req, res) => {
  res.sendFile("Books/");
});

app.get("/api/books", async (req, res) => {
  const books = await axios.get(DB_URL);
  res.json(books.data);
});

app.post("/api/upload", upload.single("book"), (req, res) => {
  if (req.file && req.body.name && req.body.subject) {
    axios.post(DB_URL, {
      bookname: req.body.name,
      filename: req.file.filename,
      subject: req.body.subject,
      location: req.file.path,
    });
    res.status(200).json({ status: "Success", file: req.file });
  } else res.status(404).json({ status: "Error" });
});

app.listen(PORT, () => {
  console.log("PORT : ", PORT, " is running");
});
