const express = require("express");
const router = express.Router();
const homeController = require("../controller/homeController");
const upload = require("../middlewares/upload");

router.get("/api", homeController.getApi);
router.get("/api/getProductApi", homeController.getProductApi);
router.get("/api/getProductApi", homeController.getProductApi);
router.get("/api/Search", homeController.Search);
// router.get("/api/productApi", homeController.productApi);
router.post("/api/loginApi", homeController.loginApi);
router.post("/api/registerApi", homeController.registerApi);
router.get(
  "/api/productUploadApi",
  upload.single("image"),
  homeController.productUploadApi
);

module.exports = router;
