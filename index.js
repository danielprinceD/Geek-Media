const express = require("express");
const cors = require("cors");
const multer = require("multer");
const serveIndex = require("serve-index");
app = express();
app.use(cors({ origin: "*" }));
app.use("/Books", serveIndex("./Books"));
app.use("/Books", express.static("./Books"));
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, "Books/");
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname);
  },
});
const upload = multer({
  storage: storage,
  fileFilter: function (req, file, cb) {
    if (file.mimetype == "application/pdf") cb(null, true);
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

app.post("/api/upload", upload.single("book"), (req, res) => {
  if (req.file) res.status(200).json({ status: "Success", file: req.file });
  else res.status(404).json({ status: "File not accepted", file: req.file });
});

app.listen(PORT, () => {
  console.log("PORT : ", PORT, " is running");
});
