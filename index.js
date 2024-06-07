const express = require("express");
const cors = require("cors");
app = express();
app.use(cors({ origin: "*" }));
const PORT = 8000 | 3000


app.listen(PORT, ()=>{
    console.log("PORT : " , PORT , " is running");
})