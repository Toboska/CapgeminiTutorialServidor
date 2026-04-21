public static final Long DELETE_CATEGORY_ID = 2L;

@Test
public void deleteWithExistsIdShouldDeleteCategory() {

    restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_CATEGORY_ID, HttpMethod.DELETE, null, Void.class);

    ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
    assertNotNull(response);
    assertEquals(2, response.getBody().size());
}

@Test
public void deleteWithNotExistsIdShouldInternalError() {

    ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CATEGORY_ID, HttpMethod.DELETE, null, Void.class);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}
