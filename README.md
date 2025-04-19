# Food Change Mood App
## Overview
Food Change Mood is an application designed to help users discover and explore various meals based on their dietary preferences, preparation time, nutritional values, and cultural origins.
The app parses a comprehensive CSV dataset containing meal information and provides multiple features to assist users in finding suitable meals.

## Features
### 1. Healthy Fast Food Finder
- Lists healthy fast food meals that can be prepared in ≤15 minutes
- Filters meals with very low total fat, saturated fat, and carbohydrates

## 2. Meal Search by Name
- Implements fast text search (Knuth-Morris-Pratt algorithm)
- Handles partial names and possible typos

## 3. Iraqi Meal Identifier
- Identifies meals tagged with "iraqi" or containing "Iraq" in description

### 4. Easy Food Suggestion
-Suggests 10 random easy-to-prepare meals meeting criteria:
* ≤30 minutes preparation
* ≤5 ingredients
* ≤6 preparation steps

### 5. Preparation Time Guessing Game
- Random meal display with 3 attempts to guess preparation time
- Provides feedback (too high/too low) after each attempt

### 6. Egg-Free Sweets
- Suggests sweets without eggs
- Allows liking/disliking to cycle through suggestions

### 7. Keto Diet Helper
- Suggests keto-friendly meals based on nutritional requirements
- Ensures no repeated suggestions

### 8. Food Search by Add Date
- Searches meals by addition date
- Handles date format exceptions and empty results

### 9. Gym Helper
- Finds meals matching desired calorie and protein amounts

### 10. Country Food Explorer
- Returns up to 20 random meals related to specified country

### 11. Ingredient Guessing Game
- Presents meal name and 3 ingredient options
- Awards points for correct guesses
- Ends after 15 correct answers or first mistake

### 12. Potato Lovers
- Shows 10 random potato-containing meals
  
### 13. High-Calorie Meal Suggester
- Suggests meals with >700 calories

### 14. Seafood Protein Ranking
- Lists seafood meals sorted by protein content (high to low)
- Displays rank, name, and protein amount

### 15. Italian Group Meals
- Finds Italian dishes suitable for large groups

## Technical Details
## Data Handling
- Parses CSV with array values in Nutrition column
- Handles null meal descriptions (2% of dataset)
- Nutrition array order: [Calories, Total Fat, Sugar, Sodium, Protein, Saturated Fat, Carbohydrates]

## Data Source 
- The application uses food.csv containing meal information with the described structure.
- [https://drive.google.com/file/d/1px860X8gO_AFHNkcNFe64e_il_bDaKSI/view?usp=sharing]
  
<img width="749" alt="Screenshot 2025-04-18 at 5 44 07 PM" src="https://github.com/user-attachments/assets/614de976-2c89-42c9-afd9-b8c9ca4b947f" />
<img width="1386" alt="Screenshot 2025-04-18 at 5 44 58 PM" src="https://github.com/user-attachments/assets/2bb17190-9daa-4bb8-8818-172e5bd372c6" />

## How to Use
1- Clone the repository
2- Import the project into your Kotlin-compatible IDE
3- Run the main application
4- Follow on-screen instructions to access different features


