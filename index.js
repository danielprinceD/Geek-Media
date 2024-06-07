const express = require("express");
const cors = require("cors");
const multer = require("multer");
app = express();
app.use(cors({ origin: "*" }));
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, "Books/");
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname);
  },
});

const upload = multer({ storage: storage });
const PORT = 8000 || 3000;

app.get("/", (req, res) => {
  res.json({ request: "GET" });
});

app.post("/api/upload", upload.single("book"), (req, res) => {
  res.status(200).json({ status: "Success", file: req.file });
});

app.use(express.static("./Books"));

app.listen(PORT, () => {
  console.log("PORT : ", PORT, " is running");
});