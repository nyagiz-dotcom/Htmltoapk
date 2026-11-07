@JavascriptInterface
public void indirilenlereKaydet(String dosyaAdi, String veri) {
    try {
        ContentResolver resolver = mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, dosyaAdi);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/json");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);
        if (uri != null) {
            try (OutputStream os = resolver.openOutputStream(uri)) {
                if (os != null) {
                    os.write(veri.getBytes("UTF-8"));
                    os.flush();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
