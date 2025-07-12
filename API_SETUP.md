# API Key Setup Guide

This guide explains how to securely configure your Football-Data.org API key for the Ballmania app.

## 🔑 Getting Your API Key

1. Visit [Football-Data.org](https://www.football-data.org/)
2. Sign up for a free account
3. Navigate to your account settings
4. Copy your API key

## ⚙️ Configuration Methods

### Method 1: Using gradle.properties (Recommended)

1. Open `gradle.properties` in the project root
2. Uncomment the last line and add your API key:
   ```properties
   FOOTBALL_API_KEY=your_actual_api_key_here
   ```

### Method 2: Using Environment Variables

Set the environment variable before building:

**Windows (PowerShell):**
```powershell
$env:FOOTBALL_API_KEY="your_actual_api_key_here"
```

**Windows (Command Prompt):**
```cmd
set FOOTBALL_API_KEY=your_actual_api_key_here
```

**macOS/Linux:**
```bash
export FOOTBALL_API_KEY="your_actual_api_key_here"
```

### Method 3: Using local.properties

1. Open `local.properties` (create if it doesn't exist)
2. Add your API key:
   ```properties
   FOOTBALL_API_KEY=your_actual_api_key_here
   ```

## 🔒 Security Notes

- **Never commit your API key to version control**
- The `gradle.properties` file is already in `.gitignore` for security
- Use different API keys for development and production
- Regularly rotate your API keys

## 🚨 Troubleshooting

If you see the error "FOOTBALL_API_KEY not found", make sure:
1. Your API key is correctly set in one of the methods above
2. You've rebuilt the project after setting the key
3. The API key is valid and active

## 📱 Testing Your Setup

After configuring your API key:
1. Clean and rebuild the project
2. Run the app
3. Navigate to any league to test API connectivity

## 🔄 API Key Rotation

If you need to change your API key:
1. Update the key using one of the methods above
2. Clean the project: `./gradlew clean`
3. Rebuild: `./gradlew build` 