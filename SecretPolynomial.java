import org.json.JSONObject;

public class SecretPolynomial {

    public static void main(String[] args) {
        // Test Case 1
        String testCase1 = "{"
                + "\"keys\": {\"n\": 4, \"k\": 3},"
                + "\"1\": {\"base\": \"10\", \"value\": \"4\"},"
                + "\"2\": {\"base\": \"2\", \"value\": \"111\"},"
                + "\"3\": {\"base\": \"10\", \"value\": \"12\"},"
                + "\"6\": {\"base\": \"4\", \"value\": \"213\"}"
                + "}";

        // Test Case 2
        String testCase2 = "{"
                + "\"keys\": {\"n\": 9, \"k\": 6},"
                + "\"1\": {\"base\": \"10\", \"value\": \"28735619723837\"},"
                + "\"2\": {\"base\": \"16\", \"value\": \"1A228867F0CA\"},"
                + "\"3\": {\"base\": \"12\", \"value\": \"32811A4AA0B7B\"},"
                + "\"4\": {\"base\": \"11\", \"value\": \"917978721331A\"},"
                + "\"5\": {\"base\": \"16\", \"value\": \"1A22886782E1\"},"
                + "\"6\": {\"base\": \"10\", \"value\": \"28735619654702\"},"
                + "\"7\": {\"base\": \"14\", \"value\": \"71AB5070CC4B\"},"
                + "\"8\": {\"base\": \"9\", \"value\": \"122662581541670\"},"
                + "\"9\": {\"base\": \"8\", \"value\": \"642121030037605\"}"
                + "}";

        // Calculate and print the constant term for both test cases
        System.out.println("Constant Term for Test Case 1: " + calculateConstantTermFromJson(testCase1));
        System.out.println("Constant Term for Test Case 2: " + calculateConstantTermFromJson(testCase2));
    }

    // Method to calculate the constant term from a JSON string
    private static double calculateConstantTermFromJson(String jsonString) {
        // Parse the JSON input
        JSONObject json = new JSONObject(jsonString);

        // Get the number of roots (n) and required roots (k)
        int n = json.getJSONObject("keys").getInt("n");

        // Create arrays to hold x and y values
        int[] xValues = new int[n];
        long[] yValues = new long[n];

        // Read the points (roots)
        for (int i = 0; i < n; i++) {
            String key = String.valueOf(i + 1);
            if (json.has(key)) {
                int x = Integer.parseInt(key);
                String yEncoded = json.getJSONObject(key).getString("value");
                int base = json.getJSONObject(key).getInt("base");

                // Decode the y value from the encoded format
                long y = Long.parseLong(yEncoded, base); // Change to Long
                xValues[i] = x;
                yValues[i] = y;
            }
        }

        // Calculate and return the constant term using Lagrange interpolation
        return calculateConstantTerm(xValues, yValues);
    }

    // Method to calculate the constant term using Lagrange interpolation
    private static double calculateConstantTerm(int[] xValues, long[] yValues) {
        double constantTerm = 0.0;

        // Lagrange interpolation to find the constant term
        for (int i = 0; i < xValues.length; i++) {
            long y1 = yValues[i]; // Get the y value

            double L = 1.0;

            for (int j = 0; j < xValues.length; j++) {
                if (i != j) {
                    L *= (0 - xValues[j]) / (double) (xValues[i] - xValues[j]);
                }
            }

            constantTerm += y1 * L;
        }

        return constantTerm;
    }
}
