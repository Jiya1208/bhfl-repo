# BFHL REST API

Spring Boot REST API for placement assignment — classifies array elements into numbers, alphabets, and special characters.

---

## Endpoint

| Method | Route | Status Code |
|--------|-------|-------------|
| POST   | /bfhl | 200 OK      |

> Only POST is used. There is no GET endpoint.

---

## ⚠️ Change Your Details First

Open `src/main/resources/application.properties`:

```properties
app.user.full-name=your_name       # lowercase, underscore between words
app.user.dob=ddmmyyyy              # e.g. 15082002
app.user.email=your@email.com
app.user.roll-number=YOUR_ROLL
```

---

## Run Locally

```bash
cd bfhl-api
./mvnw spring-boot:run
```

API runs at: `http://localhost:8080/bfhl`

## Run Tests

```bash
./mvnw test
```

---

## Postman Testing

1. Open Postman → New Request → **POST**
2. URL: `http://localhost:8080/bfhl`
3. Body → raw → **JSON**
4. Paste request body → Send

---

## Examples

### Example A
Request:
```json
{ "data": ["a", "1", "334", "4", "R", "$"] }
```
Response:
```json
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

### Example B
Request:
```json
{ "data": ["2","a","y","4","&","-","*","5","92","b"] }
```
Response:
```json
{
  "is_success": true,
  "odd_numbers": ["5"],
  "even_numbers": ["2","4","92"],
  "alphabets": ["A","Y","B"],
  "special_characters": ["&","-","*"],
  "sum": "103",
  "concat_string": "ByA"
}
```

### Example C
Request:
```json
{ "data": ["A","ABCD","DOE"] }
```
Response:
```json
{
  "is_success": true,
  "odd_numbers": [],
  "even_numbers": [],
  "alphabets": ["A","ABCD","DOE"],
  "special_characters": [],
  "sum": "0",
  "concat_string": "EoDdCbAa"
}
```

---

## concat_string Logic

1. Collect every letter from all alphabetic elements in order
2. Reverse the full list
3. Alternating caps: index 0=UPPER, 1=lower, 2=UPPER...

Example C walkthrough:
```
chars   = [A, A, B, C, D, D, O, E]
reverse = [E, O, D, D, C, B, A, A]
result  = E o D d C b A a = "EoDdCbAa"
```

---

## Project Structure

```
bfhl-api/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/bfhl/
    │   ├── BfhlApplication.java
    │   ├── controller/BfhlController.java
    │   ├── dto/BfhlRequest.java
    │   ├── dto/BfhlResponse.java
    │   ├── service/BfhlService.java          ← Interface
    │   ├── service/impl/BfhlServiceImpl.java ← Logic
    │   └── exception/GlobalExceptionHandler.java
    ├── main/resources/application.properties
    └── test/java/com/bfhl/
        ├── controller/BfhlControllerTest.java
        └── service/BfhlServiceTest.java
```

---

## Deploy on Render

1. Push to GitHub
2. Go to render.com → New → Web Service
3. Connect GitHub repo
4. Build command: `./mvnw clean package -DskipTests`
5. Start command: `java -jar target/bfhl-api-1.0.0.jar`
6. Add environment variables (your details)
7. Deploy → get URL like `https://your-app.onrender.com/bfhl`
8. Submit that URL

---

## Test Cases

| Test | Checks |
|------|--------|
| Example A | sum=339, concat=Ra |
| Example B | sum=103, concat=ByA |
| Example C | sum=0, concat=EoDdCbAa |
| Empty array | all lists empty, sum=0 |
| Only numbers | no alphabets/specials |
| Missing data field | 400 + is_success=false |
| Malformed JSON | 400 + is_success=false |
| All fields present | all 10 fields in response |
| user_id lowercase | always lowercase |
