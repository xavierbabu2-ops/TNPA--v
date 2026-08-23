import subprocess
import os

drawable_dir = "/app/applet/app/src/main/res/drawable"
os.makedirs(drawable_dir, exist_ok=True)

# 1. TNPA Official Circular Logo SVG
logo_svg = """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="600" height="600">
  <defs>
    <radialGradient id="redGrad" cx="50%" cy="50%" r="50%">
      <stop offset="60%" stop-color="#E50914" />
      <stop offset="90%" stop-color="#B7000B" />
      <stop offset="100%" stop-color="#800000" />
    </radialGradient>
    <linearGradient id="goldRim" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="#FFE082" />
      <stop offset="50%" stop-color="#FFB300" />
      <stop offset="100%" stop-color="#FF6F00" />
    </linearGradient>
    <filter id="dropShadow" x="-10%" y="-10%" width="130%" height="130%">
      <feDropShadow dx="3" dy="5" stdDeviation="4" flood-opacity="0.35"/>
    </filter>
  </defs>

  <!-- Base Transparent -->
  <rect width="600" height="600" fill="none"/>

  <!-- Outer Shadow Circle -->
  <circle cx="300" cy="300" r="280" fill="url(#redGrad)" stroke="#111111" stroke-width="4" filter="url(#dropShadow)" />
  
  <!-- Outer Gold & Black Accents -->
  <circle cx="300" cy="300" r="284" fill="none" stroke="#FFFFFF" stroke-width="2" />
  
  <!-- Inner White Disc -->
  <circle cx="300" cy="300" r="195" fill="#FFFFFF" stroke="#111111" stroke-width="4" />

  <!-- Circular Red Border Text -->
  <!-- Top Arc Text -->
  <path id="topTextArc" d="M 65 300 A 235 235 0 0 1 535 300" fill="none"/>
  <text font-family="'Mukta Malar', 'Noto Sans Tamil', 'Latha', 'Tamil Sangam MN', sans-serif" font-weight="900" font-size="34" fill="#FFFFFF" letter-spacing="1.5">
    <textPath href="#topTextArc" startOffset="50%" text-anchor="middle">
      தமிழ்நாடு பெயிண்டர்கள்
    </textPath>
  </text>

  <!-- Bottom Arc Text -->
  <path id="bottomTextArc" d="M 535 300 A 235 235 0 0 1 65 300" fill="none"/>
  <text font-family="'Mukta Malar', 'Noto Sans Tamil', 'Latha', 'Tamil Sangam MN', sans-serif" font-weight="900" font-size="30" fill="#FFFFFF" letter-spacing="1">
    <textPath href="#bottomTextArc" startOffset="50%" text-anchor="middle">
      ஓவியர்கள் முன்னேற்ற சங்கம்
    </textPath>
  </text>

  <!-- Side Symbols (Dots / Yin-Yang accents) -->
  <circle cx="95" cy="300" r="12" fill="#FFFFFF" stroke="#000000" stroke-width="2"/>
  <circle cx="95" cy="300" r="6" fill="#000000"/>
  <circle cx="505" cy="300" r="12" fill="#FFFFFF" stroke="#000000" stroke-width="2"/>
  <circle cx="505" cy="300" r="6" fill="#000000"/>

  <!-- CENTER EMBLEM: The Iconic Powerful Fist Holding Paint Brushes & Roller -->
  <g transform="translate(140, 110) scale(0.53)">
    <!-- Flat Paintbrush (Left) -->
    <g transform="rotate(-35 250 250)">
      <!-- Handle -->
      <path d="M 230 450 L 270 450 L 265 240 L 235 240 Z" fill="#D97706" stroke="#000" stroke-width="4"/>
      <!-- Ferrule -->
      <rect x="225" y="190" width="50" height="50" fill="#94A3B8" stroke="#000" stroke-width="4"/>
      <!-- Bristles -->
      <path d="M 215 90 L 285 90 L 275 190 L 225 190 Z" fill="#1E293B" stroke="#000" stroke-width="4"/>
      <path d="M 225 90 L 275 90 L 275 130 L 225 130 Z" fill="#E50914"/>
    </g>

    <!-- Fine Art Round Brush (Middle) -->
    <g transform="rotate(15 300 250)">
      <!-- Long Handle -->
      <path d="M 292 480 L 308 480 L 306 180 L 294 180 Z" fill="#451A03" stroke="#000" stroke-width="4"/>
      <!-- Ferrule -->
      <rect x="290" y="140" width="20" height="40" fill="#CBD5E1" stroke="#000" stroke-width="3"/>
      <!-- Tip -->
      <path d="M 300 70 C 285 110, 290 140, 290 140 L 310 140 C 310 140, 315 110, 300 70 Z" fill="#0F172A" stroke="#000" stroke-width="3"/>
    </g>

    <!-- Paint Roller (Right) -->
    <g transform="translate(40, -10)">
      <!-- Steel Frame -->
      <path d="M 320 320 L 370 280 L 370 140 L 420 140" fill="none" stroke="#334155" stroke-width="12" stroke-linecap="round" stroke-linejoin="round"/>
      <!-- Roller Cylinder -->
      <rect x="390" y="70" width="70" height="130" rx="14" fill="#0F172A" stroke="#000" stroke-width="5"/>
      <rect x="395" y="75" width="60" height="40" rx="8" fill="#E50914"/>
    </g>

    <!-- Strong Raised Arm & Fist (Black Silhouette) -->
    <!-- Arm -->
    <path d="M 255 580 L 345 580 L 350 420 L 250 420 Z" fill="#000000" stroke="#000" stroke-width="2"/>
    <!-- Muscle Lines -->
    <path d="M 285 540 L 290 440" stroke="#FFFFFF" stroke-width="4" stroke-linecap="round"/>
    
    <!-- Fist Clenched -->
    <path d="M 215 390 C 210 320, 250 280, 320 280 C 375 280, 400 320, 395 380 C 390 430, 350 450, 300 450 C 240 450, 220 430, 215 390 Z" fill="#000000"/>
    
    <!-- Finger Knuckles & Thumb Highlight Lines -->
    <path d="M 245 315 C 265 305, 290 305, 310 315" fill="none" stroke="#FFFFFF" stroke-width="5" stroke-linecap="round"/>
    <path d="M 240 345 C 265 335, 295 335, 325 345" fill="none" stroke="#FFFFFF" stroke-width="5" stroke-linecap="round"/>
    <path d="M 245 375 C 270 365, 305 365, 335 375" fill="none" stroke="#FFFFFF" stroke-width="5" stroke-linecap="round"/>
    <path d="M 260 405 C 285 395, 315 395, 345 405" fill="none" stroke="#FFFFFF" stroke-width="5" stroke-linecap="round"/>
    <!-- Thumb Wrapped -->
    <path d="M 335 320 C 365 340, 360 385, 330 405" fill="none" stroke="#FFFFFF" stroke-width="5" stroke-linecap="round"/>
  </g>

  <!-- Big Bold Text Below Fist -->
  <text x="300" y="450" font-family="'Arial Black', 'Impact', 'Trebuchet MS', sans-serif" font-weight="900" font-size="44" fill="#000000" text-anchor="middle" letter-spacing="3">
    TN PA²
  </text>
</svg>
"""

with open("/tmp/logo.svg", "w") as f:
    f.write(logo_svg)

subprocess.run(["convert", "-background", "none", "-density", "300", "-resize", "512x512", "/tmp/logo.svg", f"{drawable_dir}/img_tnpa_logo.png"])
print("Generated img_tnpa_logo.png")

# 2. TNPA Official Waving Flag SVG
flag_svg = """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="600" height="600">
  <defs>
    <linearGradient id="poleGrad" x1="0%" y1="0%" x2="100%" y2="0%">
      <stop offset="0%" stop-color="#E2E8F0" />
      <stop offset="30%" stop-color="#FFFFFF" />
      <stop offset="70%" stop-color="#94A3B8" />
      <stop offset="100%" stop-color="#475569" />
    </linearGradient>
    <linearGradient id="goldFinial" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="#FFF9C4" />
      <stop offset="50%" stop-color="#FFD54F" />
      <stop offset="100%" stop-color="#FF8F00" />
    </linearGradient>
    <linearGradient id="clothShadow" x1="0%" y1="0%" x2="100%" y2="0%">
      <stop offset="0%" stop-color="#000000" stop-opacity="0.1" />
      <stop offset="25%" stop-color="#FFFFFF" stop-opacity="0.2" />
      <stop offset="50%" stop-color="#000000" stop-opacity="0.15" />
      <stop offset="75%" stop-color="#FFFFFF" stop-opacity="0.15" />
      <stop offset="100%" stop-color="#000000" stop-opacity="0.25" />
    </linearGradient>
    <filter id="flagShadow" x="-10%" y="-10%" width="130%" height="130%">
      <feDropShadow dx="8" dy="12" stdDeviation="8" flood-opacity="0.3"/>
    </filter>
  </defs>

  <rect width="600" height="600" fill="none"/>

  <!-- Flag Fabric with Dynamic Waving Paths -->
  <g filter="url(#flagShadow)">
    <!-- Red Top Diagonal Half (Waving) -->
    <path d="M 120 160 C 220 130, 320 200, 420 160 C 470 140, 500 170, 520 190 L 330 350 L 120 370 Z" fill="#D32F2F"/>
    
    <!-- White Bottom Diagonal Half (Waving) -->
    <path d="M 120 370 L 330 350 L 520 190 C 530 290, 480 390, 510 410 C 420 380, 320 440, 220 400 C 170 380, 130 400, 120 390 Z" fill="#F8FAFC" stroke="#E2E8F0" stroke-width="1"/>

    <!-- Complete Connected Flag Outline with Cloth Shader Overlay -->
    <path d="M 120 160 C 220 130, 320 200, 420 160 C 470 140, 500 170, 520 190 C 530 290, 480 390, 510 410 C 420 380, 320 440, 220 400 C 170 380, 130 400, 120 390 Z" fill="url(#clothShadow)"/>
    
    <!-- Flag Fastener Bands on Pole -->
    <rect x="112" y="165" width="16" height="10" rx="3" fill="#334155"/>
    <rect x="112" y="375" width="16" height="10" rx="3" fill="#334155"/>

    <!-- Center Golden Medallion & TNPA Emblem -->
    <g transform="translate(320, 285) scale(0.68)">
      <!-- Golden Ornate Ring -->
      <circle cx="0" cy="0" r="105" fill="#FFFBEB" stroke="url(#goldFinial)" stroke-width="14"/>
      <circle cx="0" cy="0" r="95" fill="none" stroke="#D97706" stroke-width="3" stroke-dasharray="8,5"/>
      <circle cx="0" cy="0" r="88" fill="#FFFFFF" stroke="#000000" stroke-width="2"/>
      
      <!-- Mini Fist and Brushes inside Flag Medallion -->
      <path d="M -25 -25 L -55 -45 L -45 -60 L -15 -35 Z" fill="#D97706"/>
      <rect x="-60" y="-68" width="18" height="14" fill="#94A3B8"/>
      <rect x="-70" y="-85" width="22" height="20" fill="#1E293B"/>

      <!-- Roller Right -->
      <path d="M 15 -10 L 35 -20 L 35 -50 L 55 -50" fill="none" stroke="#334155" stroke-width="6"/>
      <rect x="45" y="-75" width="26" height="45" rx="6" fill="#0F172A"/>

      <!-- Raised Fist (Silhouette) -->
      <path d="M -18 35 L 18 35 L 15 -5 C 20 -20, -5 -25, -15 -10 C -25 0, -20 20, -18 35 Z" fill="#000000"/>
      <!-- Text inside flag -->
      <text x="0" y="58" font-family="'Arial Black', sans-serif" font-weight="900" font-size="20" fill="#000000" text-anchor="middle" letter-spacing="1">
        TNPA²
      </text>
    </g>
  </g>

  <!-- Stainless Steel Flagpole on Left -->
  <rect x="95" y="100" width="22" height="480" rx="6" fill="url(#poleGrad)" stroke="#334155" stroke-width="2"/>
  <!-- Pole Highlights -->
  <line x1="102" y1="105" x2="102" y2="575" stroke="#FFFFFF" stroke-width="4" stroke-linecap="round"/>
  <!-- Top Finial Orb -->
  <circle cx="106" cy="90" r="22" fill="url(#goldFinial)" stroke="#B45309" stroke-width="2" filter="url(#flagShadow)"/>
  <ellipse cx="100" cy="84" rx="8" ry="5" fill="#FFFFFF" opacity="0.8"/>
  <rect x="100" y="106" width="12" height="16" rx="2" fill="#B45309"/>
</svg>
"""

with open("/tmp/flag.svg", "w") as f:
    f.write(flag_svg)

subprocess.run(["convert", "-background", "none", "-density", "300", "-resize", "512x512", "/tmp/flag.svg", f"{drawable_dir}/img_tnpa_flag.png"])
print("Generated img_tnpa_flag.png")

# 3. State President Portrait (S. Michael Alvin)
president_svg = """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="600" height="600">
  <defs>
    <radialGradient id="bgSun" cx="30%" cy="30%" r="70%">
      <stop offset="0%" stop-color="#FFD54F" />
      <stop offset="50%" stop-color="#FF9800" />
      <stop offset="100%" stop-color="#E65100" />
    </radialGradient>
    <linearGradient id="shirtGrad" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="#FFFFFF" />
      <stop offset="70%" stop-color="#F1F5F9" />
      <stop offset="100%" stop-color="#CBD5E1" />
    </linearGradient>
    <linearGradient id="skinGrad" x1="0%" y1="0%" x2="0%" y2="100%">
      <stop offset="0%" stop-color="#E89758" />
      <stop offset="50%" stop-color="#C67638" />
      <stop offset="100%" stop-color="#9A501E" />
    </linearGradient>
    <filter id="shadow" x="-10%" y="-10%" width="130%" height="130%">
      <feDropShadow dx="0" dy="6" stdDeviation="6" flood-opacity="0.3"/>
    </filter>
  </defs>

  <rect width="600" height="600" rx="30" fill="url(#bgSun)"/>

  <!-- Tamil Nadu Map & Flag Contour in Background -->
  <path d="M 50 120 C 120 60, 220 80, 280 180 C 260 280, 200 360, 150 480 C 80 400, 30 250, 50 120 Z" fill="#D32F2F" opacity="0.35"/>
  <path d="M 380 40 L 580 40 L 580 320 L 440 260 Z" fill="#FFFFFF" opacity="0.4"/>

  <!-- Character / Leader Realistic Vector Art Portrait -->
  <!-- Body & White Shirt -->
  <g filter="url(#shadow)">
    <!-- Shoulders -->
    <path d="M 90 600 C 110 440, 200 410, 300 410 C 400 410, 490 440, 510 600 Z" fill="url(#shirtGrad)"/>
    <!-- Collar Left -->
    <path d="M 230 410 L 300 490 L 260 410 Z" fill="#E2E8F0" stroke="#94A3B8" stroke-width="2"/>
    <path d="M 220 410 L 285 490 L 195 440 Z" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="2"/>
    <!-- Collar Right -->
    <path d="M 370 410 L 300 490 L 340 410 Z" fill="#E2E8F0" stroke="#94A3B8" stroke-width="2"/>
    <path d="M 380 410 L 315 490 L 405 440 Z" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="2"/>
    <!-- Shirt Buttons & Placket -->
    <line x1="300" y1="490" x2="300" y2="600" stroke="#CBD5E1" stroke-width="3"/>
    <circle cx="300" cy="530" r="5" fill="#475569"/>
    <circle cx="300" cy="580" r="5" fill="#475569"/>
    <!-- Pocket -->
    <path d="M 375 510 L 445 510 L 445 580 C 445 595, 375 595, 375 580 Z" fill="#F8FAFC" stroke="#E2E8F0" stroke-width="2"/>
  </g>

  <!-- Neck -->
  <path d="M 255 350 L 345 350 L 345 440 L 255 440 Z" fill="#A85822"/>

  <!-- Head / Face -->
  <g filter="url(#shadow)">
    <!-- Ears -->
    <ellipse cx="205" cy="275" rx="20" ry="32" fill="#B36228"/>
    <ellipse cx="395" cy="275" rx="20" ry="32" fill="#B36228"/>
    
    <!-- Face Shape (Oval Leader) -->
    <path d="M 215 210 C 215 130, 385 130, 385 210 C 385 320, 365 370, 300 375 C 235 370, 215 320, 215 210 Z" fill="url(#skinGrad)"/>

    <!-- Hair (Neat Short Black Hair) -->
    <path d="M 205 200 C 200 100, 270 70, 300 70 C 330 70, 400 100, 395 200 C 380 140, 340 120, 300 125 C 260 120, 220 140, 205 200 Z" fill="#171717"/>

    <!-- Eyebrows -->
    <path d="M 235 205 Q 260 195 280 205" fill="none" stroke="#171717" stroke-width="7" stroke-linecap="round"/>
    <path d="M 320 205 Q 340 195 365 205" fill="none" stroke="#171717" stroke-width="7" stroke-linecap="round"/>

    <!-- Eyes -->
    <ellipse cx="258" cy="225" rx="14" ry="9" fill="#FFFFFF"/>
    <circle cx="260" cy="225" r="7" fill="#1C1917"/>
    <circle cx="262" cy="223" r="2.5" fill="#FFFFFF"/>

    <ellipse cx="342" cy="225" rx="14" ry="9" fill="#FFFFFF"/>
    <circle cx="340" cy="225" r="7" fill="#1C1917"/>
    <circle cx="342" cy="223" r="2.5" fill="#FFFFFF"/>

    <!-- Nose -->
    <path d="M 300 215 L 295 268 Q 300 278 308 275" fill="none" stroke="#843B0E" stroke-width="4" stroke-linecap="round"/>

    <!-- Charismatic Mustache (Neat Thick Black) -->
    <path d="M 250 305 Q 300 295 350 305 Q 330 325 300 318 Q 270 325 250 305 Z" fill="#171717"/>

    <!-- Mouth & Smile -->
    <path d="M 275 330 Q 300 342 325 330" fill="none" stroke="#78350F" stroke-width="4" stroke-linecap="round"/>
  </g>

  <!-- Title Ribbon at Bottom -->
  <rect x="40" y="525" width="520" height="55" rx="16" fill="#1E293B" stroke="#F59E0B" stroke-width="2" filter="url(#shadow)"/>
  <text x="300" y="550" font-family="'Noto Sans Tamil', 'Mukta Malar', sans-serif" font-weight="900" font-size="18" fill="#FDE047" text-anchor="middle">
    எஸ். மைக்கேல் ஆல்வின்
  </text>
  <text x="300" y="570" font-family="'Noto Sans Tamil', 'Mukta Malar', sans-serif" font-weight="700" font-size="13" fill="#FFFFFF" text-anchor="middle">
    மாநிலத் தலைவர் (State President)
  </text>
</svg>
"""

with open("/tmp/president.svg", "w") as f:
    f.write(president_svg)

subprocess.run(["convert", "-background", "none", "-density", "300", "-resize", "512x512", "/tmp/president.svg", f"{drawable_dir}/img_state_president.png"])
print("Generated img_state_president.png")

# 4. State General Secretary Portrait (Xavier Babu)
secretary_svg = """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="600" height="600">
  <defs>
    <radialGradient id="darkStudio" cx="50%" cy="35%" r="65%">
      <stop offset="0%" stop-color="#1E293B" />
      <stop offset="60%" stop-color="#0F172A" />
      <stop offset="100%" stop-color="#020617" />
    </radialGradient>
    <linearGradient id="skinSec" x1="0%" y1="0%" x2="0%" y2="100%">
      <stop offset="0%" stop-color="#E89B64" />
      <stop offset="50%" stop-color="#CB7B41" />
      <stop offset="100%" stop-color="#A1531F" />
    </linearGradient>
    <filter id="glow" x="-10%" y="-10%" width="130%" height="130%">
      <feDropShadow dx="0" dy="8" stdDeviation="8" flood-opacity="0.5"/>
    </filter>
  </defs>

  <rect width="600" height="600" rx="30" fill="url(#darkStudio)"/>
  
  <!-- Subtle Red & Gold Association Accent Arc Behind -->
  <circle cx="300" cy="270" r="210" fill="none" stroke="#E50914" stroke-width="4" opacity="0.3"/>
  <circle cx="300" cy="270" r="220" fill="none" stroke="#F59E0B" stroke-width="2" stroke-dasharray="10,8" opacity="0.4"/>

  <!-- Body in Crisp White Shirt -->
  <g filter="url(#glow)">
    <path d="M 90 600 C 110 420, 200 395, 300 395 C 400 395, 490 420, 510 600 Z" fill="#FFFFFF"/>
    
    <!-- Open Collar -->
    <path d="M 230 395 L 290 480 L 255 395 Z" fill="#E2E8F0"/>
    <path d="M 220 395 L 280 480 L 190 420 Z" fill="#F8FAFC" stroke="#CBD5E1" stroke-width="2"/>
    <path d="M 370 395 L 310 480 L 345 395 Z" fill="#E2E8F0"/>
    <path d="M 380 395 L 320 480 L 410 420 Z" fill="#F8FAFC" stroke="#CBD5E1" stroke-width="2"/>

    <!-- Gold Chain around neck -->
    <path d="M 260 410 Q 300 460 340 410" fill="none" stroke="#F59E0B" stroke-width="4" stroke-linecap="round"/>
    <path d="M 262 410 Q 300 460 338 410" fill="none" stroke="#FEF08A" stroke-width="2" stroke-linecap="round"/>

    <!-- Placket & Button -->
    <line x1="300" y1="480" x2="300" y2="600" stroke="#E2E8F0" stroke-width="3"/>
    <circle cx="300" cy="525" r="5" fill="#475569"/>
  </g>

  <!-- Neck -->
  <path d="M 255 330 L 345 330 L 345 425 L 255 425 Z" fill="#A85822"/>

  <!-- Head / Portrait -->
  <g filter="url(#glow)">
    <!-- Ears -->
    <ellipse cx="195" cy="265" rx="18" ry="30" fill="#B8662B"/>
    <ellipse cx="405" cy="265" rx="18" ry="30" fill="#B8662B"/>
    
    <!-- Face (Youthful Leader) -->
    <path d="M 205 190 C 205 110, 395 110, 395 190 C 395 300, 375 365, 300 370 C 225 365, 205 300, 205 190 Z" fill="url(#skinSec)"/>

    <!-- Dense Black Hair with Styling -->
    <path d="M 195 180 C 190 70, 270 50, 300 50 C 335 50, 410 70, 405 180 C 390 120, 350 95, 300 100 C 250 95, 210 120, 195 180 Z" fill="#09090B"/>

    <!-- Eyebrows -->
    <path d="M 230 190 Q 255 180 280 190" fill="none" stroke="#09090B" stroke-width="8" stroke-linecap="round"/>
    <path d="M 320 190 Q 345 180 370 190" fill="none" stroke="#09090B" stroke-width="8" stroke-linecap="round"/>

    <!-- Expressive Eyes -->
    <ellipse cx="255" cy="210" rx="15" ry="10" fill="#FFFFFF"/>
    <circle cx="258" cy="210" r="7.5" fill="#18181B"/>
    <circle cx="260" cy="208" r="2.5" fill="#FFFFFF"/>

    <ellipse cx="345" cy="210" rx="15" ry="10" fill="#FFFFFF"/>
    <circle cx="342" cy="210" r="7.5" fill="#18181B"/>
    <circle cx="344" cy="208" r="2.5" fill="#FFFFFF"/>

    <!-- Nose -->
    <path d="M 300 200 L 295 255 Q 300 265 310 260" fill="none" stroke="#7C2D12" stroke-width="4" stroke-linecap="round"/>

    <!-- Mustache & Trimmed Beard (Identical to User Photo) -->
    <!-- Mustache -->
    <path d="M 248 290 Q 300 280 352 290 Q 330 312 300 305 Q 270 312 248 290 Z" fill="#09090B"/>
    <!-- Chin Beard -->
    <path d="M 255 330 C 275 365, 325 365, 345 330 C 330 350, 270 350, 255 330 Z" fill="#09090B"/>

    <!-- Mouth -->
    <path d="M 275 318 Q 300 326 325 318" fill="none" stroke="#78350F" stroke-width="3" stroke-linecap="round"/>
  </g>

  <!-- Title Ribbon at Bottom -->
  <rect x="40" y="525" width="520" height="55" rx="16" fill="#0F172A" stroke="#EF4444" stroke-width="2" filter="url(#glow)"/>
  <text x="300" y="550" font-family="'Noto Sans Tamil', 'Mukta Malar', sans-serif" font-weight="900" font-size="18" fill="#FFFFFF" text-anchor="middle">
    சேவியர் பாபு (Xavier Babu)
  </text>
  <text x="300" y="570" font-family="'Noto Sans Tamil', 'Mukta Malar', sans-serif" font-weight="700" font-size="13" fill="#FCA5A5" text-anchor="middle">
    மாநில பொதுச் செயலாளர் (State General Secretary)
  </text>
</svg>
"""

with open("/tmp/secretary.svg", "w") as f:
    f.write(secretary_svg)

subprocess.run(["convert", "-background", "none", "-density", "300", "-resize", "512x512", "/tmp/secretary.svg", f"{drawable_dir}/img_state_general_secretary.png"])
print("Generated img_state_general_secretary.png")

# 5. State Treasurer Portrait (Sakthivel - Vanakkam Greeting)
treasurer_svg = """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="600" height="600">
  <defs>
    <radialGradient id="softStudio" cx="50%" cy="40%" r="60%">
      <stop offset="0%" stop-color="#F1F5F9" />
      <stop offset="60%" stop-color="#E2E8F0" />
      <stop offset="100%" stop-color="#CBD5E1" />
    </radialGradient>
    <linearGradient id="skinTr" x1="0%" y1="0%" x2="0%" y2="100%">
      <stop offset="0%" stop-color="#D97736" />
      <stop offset="50%" stop-color="#B45309" />
      <stop offset="100%" stop-color="#78350F" />
    </linearGradient>
    <filter id="softShadow" x="-10%" y="-10%" width="130%" height="130%">
      <feDropShadow dx="0" dy="6" stdDeviation="6" flood-opacity="0.25"/>
    </filter>
  </defs>

  <rect width="600" height="600" rx="30" fill="url(#softStudio)"/>

  <!-- Body in White Shirt -->
  <g filter="url(#softShadow)">
    <path d="M 100 600 C 120 450, 200 420, 300 420 C 400 420, 480 450, 500 600 Z" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="1.5"/>
    <path d="M 235 420 L 290 495 L 260 420 Z" fill="#E2E8F0"/>
    <path d="M 365 420 L 310 495 L 340 420 Z" fill="#E2E8F0"/>
  </g>

  <!-- Neck -->
  <path d="M 260 360 L 340 360 L 340 440 L 260 440 Z" fill="#92400E"/>

  <!-- Head / Face -->
  <g filter="url(#softShadow)">
    <!-- Ears -->
    <ellipse cx="205" cy="285" rx="18" ry="30" fill="#A16207"/>
    <ellipse cx="395" cy="285" rx="18" ry="30" fill="#A16207"/>
    
    <!-- Face -->
    <path d="M 215 210 C 215 130, 385 130, 385 210 C 385 330, 365 375, 300 380 C 235 375, 215 330, 215 210 Z" fill="url(#skinTr)"/>

    <!-- Salt & Pepper Gray Hair -->
    <path d="M 205 200 C 200 90, 270 70, 300 70 C 330 70, 400 90, 395 200 C 380 130, 345 110, 300 115 C 255 110, 220 130, 205 200 Z" fill="#334155"/>
    <path d="M 215 150 Q 300 100 385 150" stroke="#94A3B8" stroke-width="6" fill="none"/>

    <!-- Eyebrows (Graying) -->
    <path d="M 235 210 Q 260 200 280 210" fill="none" stroke="#475569" stroke-width="7" stroke-linecap="round"/>
    <path d="M 320 210 Q 340 200 365 210" fill="none" stroke="#475569" stroke-width="7" stroke-linecap="round"/>

    <!-- Eyes (Warm Smiling) -->
    <ellipse cx="258" cy="230" rx="14" ry="9" fill="#FFFFFF"/>
    <circle cx="260" cy="230" r="7" fill="#1E293B"/>
    <circle cx="262" cy="228" r="2" fill="#FFFFFF"/>

    <ellipse cx="342" cy="230" rx="14" ry="9" fill="#FFFFFF"/>
    <circle cx="340" cy="230" r="7" fill="#1E293B"/>
    <circle cx="342" cy="228" r="2" fill="#FFFFFF"/>

    <!-- Nose -->
    <path d="M 300 220 L 295 272 Q 300 282 308 280" fill="none" stroke="#78350F" stroke-width="4" stroke-linecap="round"/>

    <!-- Full Salt-and-Pepper Gray Beard & Mustache -->
    <path d="M 225 280 C 225 380, 375 380, 375 280 C 355 330, 245 330, 225 280 Z" fill="#475569"/>
    <!-- Gray Beard Texture -->
    <path d="M 245 340 Q 300 380 355 340" stroke="#CBD5E1" stroke-width="8" fill="none" stroke-linecap="round"/>
    <path d="M 260 360 Q 300 390 340 360" stroke="#F1F5F9" stroke-width="5" fill="none" stroke-linecap="round"/>

    <!-- Big Happy Smile with Teeth -->
    <path d="M 260 305 Q 300 335 340 305 Z" fill="#FFFFFF" stroke="#78350F" stroke-width="2"/>
    <line x1="265" y1="315" x2="335" y2="315" stroke="#E2E8F0" stroke-width="1.5"/>
  </g>

  <!-- FOLDED HANDS GREETING (Vanakkam / Namaste) in Center Front -->
  <g filter="url(#softShadow)" transform="translate(0, 10)">
    <!-- Wrists & Hands coming together -->
    <!-- Left Hand -->
    <path d="M 260 560 L 285 430 Q 300 420 300 440 L 285 560 Z" fill="#C26A28" stroke="#78350F" stroke-width="2"/>
    <!-- Right Hand -->
    <path d="M 340 560 L 315 430 Q 300 420 300 440 L 315 560 Z" fill="#B45E1B" stroke="#78350F" stroke-width="2"/>
    <!-- Joined Fingers / Palms -->
    <path d="M 285 430 Q 300 410 315 430 L 310 520 L 290 520 Z" fill="#D97736" stroke="#78350F" stroke-width="2"/>
    <!-- Finger detail lines -->
    <line x1="295" y1="440" x2="292" y2="480" stroke="#92400E" stroke-width="2"/>
    <line x1="305" y1="440" x2="308" y2="480" stroke="#92400E" stroke-width="2"/>
    <!-- White shirt sleeves -->
    <path d="M 230 600 L 270 540 L 290 600 Z" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="2"/>
    <path d="M 370 600 L 330 540 L 310 600 Z" fill="#FFFFFF" stroke="#CBD5E1" stroke-width="2"/>
  </g>

  <!-- Title Ribbon at Bottom -->
  <rect x="40" y="525" width="520" height="55" rx="16" fill="#1E293B" stroke="#10B981" stroke-width="2" filter="url(#softShadow)"/>
  <text x="300" y="550" font-family="'Noto Sans Tamil', 'Mukta Malar', sans-serif" font-weight="900" font-size="18" fill="#6EE7B7" text-anchor="middle">
    சக்திவேல் (Sakthivel)
  </text>
  <text x="300" y="570" font-family="'Noto Sans Tamil', 'Mukta Malar', sans-serif" font-weight="700" font-size="13" fill="#FFFFFF" text-anchor="middle">
    மாநில பொருளாளர் (State Treasurer)
  </text>
</svg>
"""

with open("/tmp/treasurer.svg", "w") as f:
    f.write(treasurer_svg)

subprocess.run(["convert", "-background", "none", "-density", "300", "-resize", "512x512", "/tmp/treasurer.svg", f"{drawable_dir}/img_state_treasurer.png"])
print("Generated img_state_treasurer.png")

print("All 5 original high-resolution assets generated successfully!")
