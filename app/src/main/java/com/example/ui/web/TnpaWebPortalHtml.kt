package com.example.ui.web

object TnpaWebPortalHtml {
  fun getFullPortalHtml(): String {
    return """<!DOCTYPE html>
<html lang="ta">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் | TNPA Official Portal</title>
  <meta name="description" content="தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கத்தின் (TNPA) அதிகாரப்பூர்வ இணையதளம். டிஜிட்டல் உறுப்பினர் அடையாள அட்டை, கட்டுமானத் தொழிலாளர் நலவாரிய நிதி உதவிகள், நேரலை TV மற்றும் AI வழிகாட்டி சேவைகள்.">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Mukta+Malar:wght@400;600;700;800&family=Inter:wght@400;600;700;900&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary: #E60000;
      --primary-dark: #990000;
      --primary-light: #FF3333;
      --dark: #121212;
      --dark-surface: #1E1E1E;
      --dark-card: #252525;
      --gold: #FFD700;
      --text-white: #FFFFFF;
      --text-muted: #A0A0A0;
      --border: #333333;
      --green: #10B981;
    }

    * { margin:0; padding:0; box-sizing:border-box; font-family:'Mukta Malar', 'Inter', -apple-system, sans-serif; -webkit-tap-highlight-color: transparent; }
    body { background: var(--dark); color: var(--text-white); padding-bottom: 70px; line-height: 1.5; }
    
    /* Top Header Bar */
    .top-bar {
      background: linear-gradient(90deg, #990000, #E60000, #121212);
      padding: 14px 16px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 2px solid var(--primary);
      position: sticky;
      top: 0;
      z-index: 100;
      box-shadow: 0 4px 12px rgba(0,0,0,0.5);
    }
    .brand-logo {
      display: flex;
      align-items: center;
      gap: 10px;
    }
    .logo-emblem {
      width: 44px;
      height: 44px;
      background: #FFFFFF;
      border: 2px solid var(--gold);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 900;
      font-size: 16px;
      color: var(--primary);
      box-shadow: 0 2px 6px rgba(0,0,0,0.3);
    }
    .brand-title h1 {
      font-size: 15px;
      font-weight: 800;
      color: #FFFFFF;
      line-height: 1.2;
    }
    .brand-title p {
      font-size: 10px;
      color: #FFD700;
      font-weight: 600;
    }
    .live-badge-top {
      background: #000000;
      border: 1px solid var(--primary-light);
      padding: 4px 8px;
      border-radius: 20px;
      display: flex;
      align-items: center;
      gap: 6px;
      font-size: 11px;
      font-weight: 700;
      color: #FF4D4D;
    }
    .live-dot {
      width: 8px;
      height: 8px;
      background: #FF0000;
      border-radius: 50%;
      animation: pulse 1.2s infinite;
    }
    @keyframes pulse { 0% { opacity: 1; transform: scale(1); } 50% { opacity: 0.4; transform: scale(1.3); } 100% { opacity: 1; transform: scale(1); } }

    /* Marquee News Bar */
    .news-ticker {
      background: #000000;
      border-bottom: 1px solid #333333;
      padding: 6px 12px;
      display: flex;
      align-items: center;
      font-size: 11px;
      overflow: hidden;
      white-space: nowrap;
    }
    .ticker-tag {
      background: var(--primary);
      color: #FFF;
      font-weight: 800;
      padding: 2px 6px;
      border-radius: 4px;
      margin-right: 10px;
      font-size: 10px;
      flex-shrink: 0;
    }
    .ticker-content {
      display: inline-block;
      animation: marquee 25s linear infinite;
      color: #E2E8F0;
    }
    @keyframes marquee { 0% { transform: translateX(100%); } 100% { transform: translateX(-100%); } }

    /* Web Navigation Tabs */
    .nav-tabs {
      display: flex;
      background: var(--dark-surface);
      overflow-x: auto;
      border-bottom: 1px solid var(--border);
      padding: 4px 8px;
      gap: 6px;
      scrollbar-width: none;
    }
    .nav-tabs::-webkit-scrollbar { display: none; }
    .nav-tab-btn {
      background: transparent;
      border: 1px solid transparent;
      color: var(--text-muted);
      padding: 8px 14px;
      border-radius: 8px;
      font-size: 12px;
      font-weight: 700;
      white-space: nowrap;
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 6px;
      transition: all 0.2s;
    }
    .nav-tab-btn.active {
      background: var(--primary);
      color: #FFFFFF;
      border-color: var(--primary-light);
      box-shadow: 0 2px 8px rgba(230, 0, 0, 0.4);
    }

    /* Container & Sections */
    .container { padding: 14px; max-width: 800px; margin: 0 auto; }
    .tab-content { display: none; }
    .tab-content.active { display: block; animation: fadeIn 0.3s ease-in-out; }
    @keyframes fadeIn { from { opacity: 0; transform: translateY(6px); } to { opacity: 1; transform: translateY(0); } }

    /* Cards */
    .card {
      background: var(--dark-surface);
      border: 1px solid var(--border);
      border-radius: 12px;
      padding: 14px;
      margin-bottom: 14px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    }
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 10px;
      border-bottom: 1px solid #333333;
      padding-bottom: 8px;
    }
    .card-title {
      font-size: 14px;
      font-weight: 800;
      color: #FFFFFF;
      display: flex;
      align-items: center;
      gap: 6px;
    }
    .badge {
      background: var(--primary);
      color: #FFF;
      font-size: 10px;
      font-weight: 700;
      padding: 3px 8px;
      border-radius: 6px;
    }

    /* Form Elements */
    .form-group { margin-bottom: 12px; }
    .form-label { display: block; font-size: 11px; font-weight: 700; color: #CCCCCC; margin-bottom: 4px; }
    .form-input, .form-select {
      width: 100%;
      background: var(--dark-card);
      border: 1px solid var(--border);
      color: #FFFFFF;
      padding: 10px 12px;
      border-radius: 8px;
      font-size: 13px;
      outline: none;
      transition: border-color 0.2s;
    }
    .form-input:focus, .form-select:focus {
      border-color: var(--primary-light);
      box-shadow: 0 0 0 2px rgba(230,0,0,0.3);
    }

    /* Buttons */
    .btn {
      width: 100%;
      padding: 11px;
      border-radius: 8px;
      font-size: 13px;
      font-weight: 800;
      cursor: pointer;
      border: none;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      transition: transform 0.1s, opacity 0.2s;
    }
    .btn:active { transform: scale(0.98); }
    .btn-primary { background: linear-gradient(135deg, #E60000, #990000); color: #FFF; box-shadow: 0 4px 12px rgba(230,0,0,0.4); }
    .btn-dark { background: #333333; color: #FFF; }
    .btn-gold { background: #FFD700; color: #121212; }
    .btn-green { background: #10B981; color: #FFF; }

    /* Video Player Screen */
    .video-box {
      width: 100%;
      height: 210px;
      background: #000;
      border-radius: 12px;
      overflow: hidden;
      position: relative;
      border: 2px solid var(--border);
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }
    .video-canvas {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    .video-overlay {
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      padding: 8px 12px;
      background: linear-gradient(0deg, rgba(0,0,0,0.9), transparent);
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    /* ID Card Layout */
    .id-card-preview {
      background: linear-gradient(135deg, #990000 0%, #E60000 50%, #121212 100%);
      border: 2px solid var(--gold);
      border-radius: 14px;
      padding: 14px;
      color: #FFF;
      position: relative;
      box-shadow: 0 8px 24px rgba(0,0,0,0.6);
      margin-top: 14px;
    }
    .id-card-header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid rgba(255,255,255,0.2);
      padding-bottom: 8px;
      margin-bottom: 10px;
    }
    .id-card-body {
      display: flex;
      gap: 12px;
    }
    .id-photo-frame {
      width: 75px;
      height: 90px;
      background: #FFF;
      border-radius: 8px;
      border: 2px solid var(--gold);
      display: flex;
      align-items: center;
      justify-content: center;
      color: #121212;
      font-size: 28px;
      flex-shrink: 0;
      overflow: hidden;
    }
    .id-info-grid {
      flex-grow: 1;
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 4px 8px;
      font-size: 11px;
    }
    .id-field-label { color: #FFD700; font-size: 9px; font-weight: 700; }
    .id-field-val { font-weight: 700; color: #FFF; }

    /* Gallery Grid */
    .gallery-grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 10px;
    }
    .art-card {
      background: var(--dark-card);
      border: 1px solid var(--border);
      border-radius: 10px;
      overflow: hidden;
      display: flex;
      flex-direction: column;
    }
    .art-thumb {
      height: 100px;
      background: linear-gradient(45deg, #E60000, #1E1E1E);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 32px;
      position: relative;
    }
    .art-details { padding: 8px; flex-grow: 1; }
    .art-title { font-size: 11px; font-weight: 800; color: #FFF; line-height: 1.3; }
    .art-artist { font-size: 10px; color: var(--gold); margin-top: 2px; }

    /* AI Calculator Result */
    .calc-result-box {
      background: rgba(16, 185, 129, 0.1);
      border: 1px solid var(--green);
      border-radius: 10px;
      padding: 12px;
      margin-top: 10px;
    }

    /* Bottom Status Bar */
    .bottom-nav {
      position: fixed;
      bottom: 0;
      left: 0;
      right: 0;
      background: #0D0D0D;
      border-top: 1px solid var(--border);
      display: flex;
      justify-content: space-around;
      padding: 8px 0;
      z-index: 100;
    }
    .bottom-item {
      color: var(--text-muted);
      font-size: 10px;
      font-weight: 700;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 2px;
      text-decoration: none;
      cursor: pointer;
    }
    .bottom-item.active { color: var(--primary-light); }
  </style>
</head>
<body>

  <!-- Top Brand Navigation Header -->
  <header class="top-bar">
    <div class="brand-logo">
      <div class="logo-emblem">TNPA</div>
      <div class="brand-title">
        <h1>தமிழ்நாடு பெயிண்டர்கள் & ஓவியர்கள் முன்னேற்ற சங்கம்</h1>
        <p>TNPA² Official Web Portal • மதுரை தலைமையகம்</p>
      </div>
    </div>
    <div class="live-badge-top" onclick="switchTab('tab-live')">
      <div class="live-dot"></div>
      <span>LIVE TV</span>
    </div>
  </header>

  <!-- Breaking News Marquee -->
  <div class="news-ticker">
    <span class="ticker-tag">அறிவிப்பு</span>
    <div class="ticker-content">
      📢 TNPA² உறுப்பினர் சேர்க்கை 2026 துவங்கப்பட்டுள்ளது! • தமிழ்நாடு உடலுழைப்பு தொழிலாளர்கள் நலவாரிய நலத்திட்ட உதவி முகாம் மதுரையில் நடைபெறுகிறது • நேரலை TV-யில் சிறப்பு செய்முறை வகுப்பு தொடர்கிறது!
    </div>
  </div>

  <!-- Interactive Navigation Tabs -->
  <nav class="nav-tabs">
    <button class="nav-tab-btn active" onclick="switchTab('tab-home')">🏠 முகப்பு (Home)</button>
    <button class="nav-tab-btn" onclick="switchTab('tab-register')">🪪 உறுப்பினர் பதிவு & ID</button>
    <button class="nav-tab-btn" onclick="switchTab('tab-live')">📺 TNPA² Live TV</button>
    <button class="nav-tab-btn" onclick="switchTab('tab-gallery')">🎨 ஓவியக் கலைக்கூடம்</button>
    <button class="nav-tab-btn" onclick="switchTab('tab-welfare')">⚖️ நலவாரியம் & கால்குலேட்டர்</button>
    <button class="nav-tab-btn" onclick="switchTab('tab-ai')">🤖 AI பெயிண்டர் வழிகாட்டி</button>
    <button class="nav-tab-btn" onclick="switchTab('tab-officers')">👥 நிர்வாகிகள்</button>
    <button class="nav-tab-btn" onclick="switchTab('tab-contact')">📍 தொடர்புகள் & உதவி</button>
  </nav>

  <div class="container">

    <!-- ================= TAB 1: HOME ================= -->
    <section id="tab-home" class="tab-content active">
      <div class="card" style="background: linear-gradient(135deg, #2A0808, #1C1C1C); border-left: 4px solid var(--primary);">
        <div class="card-header">
          <span class="card-title">🏛️ சங்கத்தின் அதிகாரப்பூர்வ இணையதளம்</span>
          <span class="badge">HQ மதுரை</span>
        </div>
        <p style="font-size: 13px; color: #E2E8F0; line-height: 1.6;">
          தமிழ்நாடு முழுவதும் உள்ள லட்சக்கணக்கான கட்டிடப் பெயிண்டர்கள், கலை ஓவியர்கள், போர்டு ரைட்டர்கள் மற்றும் பட்டி-ஸ்ப்ரே தொழிலாளர்களின் வாழ்வாதாரத்தைப் பாதுகாக்கும் முதன்மைச் சங்கம்.
        </p>
        <div style="background: rgba(220, 38, 38, 0.15); border: 1px dashed var(--gold); border-radius: 6px; padding: 6px 10px; margin: 8px 0; font-size: 11px; color: var(--gold); font-weight: bold;">
          🏛️ தமிழக அரசால் அங்கீகரிக்கப்பட்ட தொழிற்சங்க பதிவெண்:<br><span style="color: #FFF; font-family: monospace;">TNMDUJCLMDUTU-TNMDUJCLMDUTU-50-26-0044</span>
        </div>
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 8px; margin-top: 12px;">
          <button class="btn btn-primary" onclick="switchTab('tab-register')">🪪 உறுப்பினர் சேர்க்கை</button>
          <button class="btn btn-gold" onclick="switchTab('tab-live')">📺 நேரலை ஒளிபரப்பு</button>
        </div>
      </div>

      <!-- Key Statistics -->
      <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px; margin-bottom: 14px;">
        <div class="card" style="text-align: center; padding: 10px; margin-bottom: 0;">
          <div style="font-size: 18px; font-weight: 900; color: var(--primary-light);">38</div>
          <div style="font-size: 10px; color: var(--text-muted);">மாவட்ட கிளைகள்</div>
        </div>
        <div class="card" style="text-align: center; padding: 10px; margin-bottom: 0;">
          <div style="font-size: 18px; font-weight: 900; color: var(--gold);">45,000+</div>
          <div style="font-size: 10px; color: var(--text-muted);">பதிவு பெற்றோர்</div>
        </div>
        <div class="card" style="text-align: center; padding: 10px; margin-bottom: 0;">
          <div style="font-size: 18px; font-weight: 900; color: var(--green);">24/7</div>
          <div style="font-size: 10px; color: var(--text-muted);">தொழிலாளர் உதவி</div>
        </div>
      </div>

      <!-- Quick Services Grid -->
      <div class="card">
        <div class="card-header">
          <span class="card-title">⚡ விரைவு இணைய சேவைகள்</span>
        </div>
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
          <div style="background: var(--dark-card); padding: 10px; border-radius: 8px; border-left: 3px solid var(--gold); cursor: pointer;" onclick="switchTab('tab-welfare')">
            <b style="font-size: 12px;">⚖️ நலவாரிய ஓய்வூதியம்</b>
            <p style="font-size: 10px; color: var(--text-muted);">மாதாந்திர ஓய்வூதியம் & நிதி</p>
          </div>
          <div style="background: var(--dark-card); padding: 10px; border-radius: 8px; border-left: 3px solid var(--primary); cursor: pointer;" onclick="switchTab('tab-gallery')">
            <b style="font-size: 12px;">🎨 ஓவியக் கண்காட்சி</b>
            <p style="font-size: 10px; color: var(--text-muted);">தமிழ்நாடு கலைஞர்கள் படைப்பு</p>
          </div>
          <div style="background: var(--dark-card); padding: 10px; border-radius: 8px; border-left: 3px solid var(--green); cursor: pointer;" onclick="switchTab('tab-ai')">
            <b style="font-size: 12px;">🤖 AI பெயிண்ட் கால்குலேட்டர்</b>
            <p style="font-size: 10px; color: var(--text-muted);">சதுர அடி & லிட்டர் கணக்கீடு</p>
          </div>
          <div style="background: var(--dark-card); padding: 10px; border-radius: 8px; border-left: 3px solid #38BDF8; cursor: pointer;" onclick="switchTab('tab-contact')">
            <b style="font-size: 12px;">📞 அவசர உதவி மையம்</b>
            <p style="font-size: 10px; color: var(--text-muted);">+91-9443212345</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= TAB 2: REGISTRATION & ID CARD ================= -->
    <section id="tab-register" class="tab-content">
      <div class="card">
        <div class="card-header">
          <span class="card-title">🪪 உறுப்பினர் பதிவு & உடனடி டிஜிட்டல் ID அட்டை</span>
          <span class="badge">Online Reg</span>
        </div>
        <form id="regForm" onsubmit="event.preventDefault(); generateWebIdCard();">
          <div class="form-group">
            <label class="form-label">பெயர் (Name in English & Tamil) *</label>
            <input type="text" id="regName" class="form-input" placeholder="எ.கா: K. முத்துக்குமார் (Muthukumar)" required>
          </div>
          <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 8px;">
            <div class="form-group">
              <label class="form-label">வயது (Age) *</label>
              <input type="number" id="regAge" class="form-input" placeholder="எ.கா: 34" min="18" max="75" required>
            </div>
            <div class="form-group">
              <label class="form-label">இரத்த வகை (Blood Group)</label>
              <select id="regBlood" class="form-select">
                <option value="O+ve">O+ve</option>
                <option value="A+ve">A+ve</option>
                <option value="B+ve" selected>B+ve</option>
                <option value="AB+ve">AB+ve</option>
                <option value="O-ve">O-ve</option>
                <option value="A-ve">A-ve</option>
              </select>
            </div>
          </div>
          <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 8px;">
            <div class="form-group">
              <label class="form-label">அனுபவம் (Years Exp) *</label>
              <input type="number" id="regExp" class="form-input" placeholder="எ.கா: 12 ஆண்டுகள்" required>
            </div>
            <div class="form-group">
              <label class="form-label">மாவட்டம் (District) *</label>
              <select id="regDistrict" class="form-select">
                <option value="திருச்சிராப்பள்ளி (Trichy)" selected>திருச்சிராப்பள்ளி</option>
                <option value="சென்னை (Chennai)">சென்னை</option>
                <option value="மதுரை (Madurai)">மதுரை</option>
                <option value="கோயம்புத்தூர் (Coimbatore)">கோயம்புத்தூர்</option>
                <option value="சேலம் (Salem)">சேலம்</option>
                <option value="திருநெல்வேலி (Tirunelveli)">திருநெல்வேலி</option>
                <option value="தஞ்சாவூர் (Thanjavur)">தஞ்சாவூர்</option>
                <option value="ஈரோடு (Erode)">ஈரோடு</option>
                <option value="வேலூர் (Vellore)">வேலூர்</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="form-label">தொழில் பிரிவு (Trade Specialization) *</label>
            <select id="regTrade" class="form-select">
              <option value="சுவர் & பில்டிங் பெயிண்டிங் (Wall Painting)">சுவர் & பில்டிங் பெயிண்டிங்</option>
              <option value="3D & ஸ்டென்சில் ஓவியக் கலை (3D Art)">3D & ஸ்டென்சில் ஓவியக் கலை</option>
              <option value="போர்டு ரைட்டிங் & ஆயில் பெயிண்டிங்">போர்டு ரைட்டிங் & ஆயில் பெயிண்டிங்</option>
              <option value="ஸ்ப்ரே & எனாமல் பாலிஷ் (Spray & Polish)">ஸ்ப்ரே & எனாமல் பாலிஷ்</option>
              <option value="பட்டி & பிரைமர் ஒர்க் (Putty & Primer)">பட்டி & பிரைமர் ஒர்க்</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">அலைபேசி எண் (Mobile No) *</label>
            <input type="tel" id="regPhone" class="form-input" placeholder="10 இலக்க எண்" maxlength="10" required>
          </div>
          <button type="submit" class="btn btn-primary">உறுப்பினர் அட்டை உருவாக்கு (Generate Card)</button>
        </form>

        <!-- Digital ID Card Result -->
        <div id="idCardResult" style="display: none;">
          <div class="id-card-preview" id="cardCanvasWrap">
            <div class="id-card-header">
              <div style="display: flex; align-items: center; gap: 6px;">
                <div style="background: #FFF; color: #E60000; font-weight: 900; padding: 2px 6px; border-radius: 4px; font-size: 11px;">TNPA²</div>
                <b style="font-size: 11px;">தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் சங்கம்</b>
              </div>
              <span style="font-size: 10px; background: rgba(0,0,0,0.5); padding: 2px 6px; border-radius: 4px; color: #FFD700;">உறுப்பினர் அட்டை</span>
            </div>
            <div class="id-card-body">
              <div class="id-photo-frame">👨‍🎨</div>
              <div class="id-info-grid">
                <div>
                  <div class="id-field-label">உறுப்பினர் பெயர்</div>
                  <div class="id-field-val" id="cardNameVal">-</div>
                </div>
                <div>
                  <div class="id-field-label">உறுப்பினர் எண் (ID)</div>
                  <div class="id-field-val" id="cardIdVal">TNPA-2026-8941</div>
                </div>
                <div>
                  <div class="id-field-label">வயது & இரத்த வகை</div>
                  <div class="id-field-val" id="cardAgeVal">-</div>
                </div>
                <div>
                  <div class="id-field-label">அனுபவம்</div>
                  <div class="id-field-val" id="cardExpVal">-</div>
                </div>
                <div style="grid-column: span 2;">
                  <div class="id-field-label">தொழில் பிரிவு</div>
                  <div class="id-field-val" id="cardTradeVal">-</div>
                </div>
                <div>
                  <div class="id-field-label">மாவட்டம்</div>
                  <div class="id-field-val" id="cardDistVal">-</div>
                </div>
                <div>
                  <div class="id-field-label">QR சரிபார்ப்பு</div>
                  <div class="id-field-val" style="color: #10B981;">✓ VERIFIED</div>
                </div>
              </div>
            </div>
          </div>
          <div style="margin-top: 10px; display: flex; gap: 8px;">
            <button class="btn btn-green" onclick="alert('டிஜிட்டல் உறுப்பினர் அட்டை வெற்றிகரமாக சேமிக்கப்பட்டது!')">💾 அட்டையை பதிவிறக்கு</button>
            <button class="btn btn-primary" onclick="shareWebCard()">📲 வாட்ஸ்அப் பகிர்</button>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= TAB 3: LIVE TV ================= -->
    <section id="tab-live" class="tab-content">
      <div class="card">
        <div class="card-header">
          <span class="card-title">📺 TNPA² Live TV - நேரலை ஒளிபரப்பு</span>
          <span class="badge" style="background: #10B981;">HLS 1080p</span>
        </div>

        <div class="video-box">
          <canvas id="liveTvCanvas" class="video-canvas" width="400" height="210"></canvas>
          <div class="video-overlay">
            <div>
              <b style="font-size: 12px; color: #FFF;">🔴 நேரலை: பெயிண்டிங் வண்ணக்கலவை டெமோ</b>
              <p style="font-size: 10px; color: #FFD700;">பார்வையாளர்கள்: <span id="viewerCount">1,482</span> பேர்</p>
            </div>
            <button class="btn btn-primary" style="width: auto; padding: 4px 10px; font-size: 11px;" onclick="toggleLivePlay()">
              <span id="playBtnText">⏸ இடைநிறுத்து</span>
            </button>
          </div>
        </div>

        <div style="margin-top: 12px; display: flex; gap: 8px;">
          <button class="btn btn-dark" style="font-size: 11px;" onclick="alert('RTMP Ingest: rtmp://live.tnpa2tv.in/live\nStream Key: tnpa2_live_secret_key_2026')">📡 ஸ்ட்ரீம் கீ தகவல்</button>
          <button class="btn btn-dark" style="font-size: 11px;" onclick="alert('HLS Master URL: https://stream.tnpa2tv.in/live/master.m3u8')">🔗 HLS இணைப்பு</button>
        </div>

        <!-- Live Chat Box -->
        <div style="margin-top: 14px; background: var(--dark-card); border-radius: 8px; padding: 10px;">
          <b style="font-size: 11px; color: var(--gold);">💬 நேரலை கலந்துரையாடல் (Live Chat)</b>
          <div id="chatMessages" style="height: 100px; overflow-y: auto; font-size: 11px; margin: 8px 0; color: #DDD;">
            <p><b>சுரேஷ் (சேலம்):</b> அக்ரிலிக் எமல்ஷனுக்கு எவ்வளவு தண்ணீர் கலக்க வேண்டும் அண்ணா?</p>
            <p><b>ரவி (திருச்சி):</b> வாழ்த்துக்கள் TNPA! நேரலை ஒளிபரப்பு மிக அருமையாக உள்ளது.</p>
            <p><b>குமார் (மதுரை):</b> நலவாரிய அட்டை புதுப்பிக்க கடைசி தேதி என்ன?</p>
          </div>
          <div style="display: flex; gap: 6px;">
            <input type="text" id="chatInput" class="form-input" placeholder="உங்கள் கருத்தை பதிவிடுக..." style="padding: 6px 8px; font-size: 11px;">
            <button class="btn btn-primary" style="width: auto; padding: 6px 12px; font-size: 11px;" onclick="sendWebChat()">அனுப்புக</button>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= TAB 4: ART GALLERY ================= -->
    <section id="tab-gallery" class="tab-content">
      <div class="card">
        <div class="card-header">
          <span class="card-title">🎨 தமிழ்நாடு ஓவியர்கள் கலைக்கூடம்</span>
          <span class="badge">600+ படைப்புகள்</span>
        </div>
        <p style="font-size: 11px; color: var(--text-muted); margin-bottom: 10px;">
          மாநிலத்தின் தலைசிறந்த சுவர் ஓவியங்கள், கேன்வாஸ் ஆயில் பெயிண்டிங் & 3D சித்திரங்கள்:
        </p>

        <div class="gallery-grid">
          <div class="art-card">
            <div class="art-thumb">🏛️</div>
            <div class="art-details">
              <div class="art-title">தஞ்சை பெரிய கோவில் பிரம்மாண்ட சுவர் ஓவியம்</div>
              <div class="art-artist">ஓவியர்: சுந்தரம் (தஞ்சாவூர்)</div>
              <button class="btn btn-dark" style="margin-top: 6px; padding: 4px; font-size: 10px;" onclick="likeArt(this)">❤️ 640 Likes</button>
            </div>
          </div>
          <div class="art-card">
            <div class="art-thumb">🌊</div>
            <div class="art-details">
              <div class="art-title">3D நீர்வீழ்ச்சி & பாறை சுவர் கலை (Mural)</div>
              <div class="art-artist">ஓவியர்: செல்வராஜ் (திருச்சி)</div>
              <button class="btn btn-dark" style="margin-top: 6px; padding: 4px; font-size: 10px;" onclick="likeArt(this)">❤️ 890 Likes</button>
            </div>
          </div>
          <div class="art-card">
            <div class="art-thumb">🛕</div>
            <div class="art-details">
              <div class="art-title">மீனாட்சி அம்மன் கோவில் பாரம்பரிய சுதை ஓவியம்</div>
              <div class="art-artist">ஓவியர்: கார்த்திகேயன் (மதுரை)</div>
              <button class="btn btn-dark" style="margin-top: 6px; padding: 4px; font-size: 10px;" onclick="likeArt(this)">❤️ 752 Likes</button>
            </div>
          </div>
          <div class="art-card">
            <div class="art-thumb">✍️</div>
            <div class="art-details">
              <div class="art-title">நவீன வணிக நியான் லெட்டரிங் & போர்டு</div>
              <div class="art-artist">ஓவியர்: விஜயகுமார் (கோவை)</div>
              <button class="btn btn-dark" style="margin-top: 6px; padding: 4px; font-size: 10px;" onclick="likeArt(this)">❤️ 389 Likes</button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= TAB 5: WELFARE & PENSION CALCULATOR ================= -->
    <section id="tab-welfare" class="tab-content">
      <div class="card">
        <div class="card-header">
          <span class="card-title">⚖️ கட்டுமான & உடலுழைப்பு தொழிலாளர்கள் நலவாரியம்</span>
          <span class="badge" style="background: var(--gold); color: #121212;">அரசு நிதி உதவி</span>
        </div>
        <p style="font-size: 12px; color: #E2E8F0; line-height: 1.5; margin-bottom: 12px;">
          தமிழ்நாடு கட்டுமானத் தொழிலாளர்கள் நலவாரியத்தின் மூலம் வழங்கப்படும் உதவித்தொகை மற்றும் ஓய்வூதிய கணக்கீடு:
        </p>

        <div class="form-group">
          <label class="form-label">உங்கள் வயது (Your Current Age):</label>
          <input type="number" id="calcAge" class="form-input" value="38" oninput="calculatePension()">
        </div>
        <div class="form-group">
          <label class="form-label">தொழிலாளர் நலவாரிய பதிவு காலம் (Membership Years):</label>
          <input type="number" id="calcYears" class="form-input" value="10" oninput="calculatePension()">
        </div>

        <div class="calc-result-box" id="calcOutput">
          <b style="font-size: 12px; color: #10B981;">📋 உங்கள் நலவாரிய உரிமை மதிப்பீடு:</b>
          <div style="font-size: 11px; margin-top: 6px; color: #FFF;">
            • மாதாந்திர ஓய்வூதியம் (60 வயதுக்கு பின்): <b>₹1,000 / மாதம்</b><br>
            • இயற்கை மரண உதவித்தொகை: <b>₹20,000</b><br>
            • விபத்து மரண காப்பீட்டு நிதி: <b>₹5,00,000</b><br>
            • பெண் குழந்தைகள் திருமண உதவி: <b>₹20,000</b><br>
            • கண் கண்ணாடி உதவித்தொகை: <b>₹500</b>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= TAB 6: AI PAINTER ASSISTANT ================= -->
    <section id="tab-ai" class="tab-content">
      <div class="card">
        <div class="card-header">
          <span class="card-title">🤖 AI பெயிண்டர் வழிகாட்டி & லிட்டர் கணக்கீடு</span>
          <span class="badge" style="background: #8B5CF6;">Gemini Powered</span>
        </div>
        <p style="font-size: 11px; color: var(--text-muted); margin-bottom: 10px;">
          சுவர் நீளம், அகலம் கொடுத்தால் தேவையான பிரைமர், பட்டி மற்றும் எமல்ஷன் பெயிண்ட் அளவை கணக்கிடலாம்:
        </p>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 8px;">
          <div class="form-group">
            <label class="form-label">சுவர் நீளம் (Length in Feet):</label>
            <input type="number" id="wallLength" class="form-input" value="20" oninput="estimatePaint()">
          </div>
          <div class="form-group">
            <label class="form-label">சுவர் உயரம் (Height in Feet):</label>
            <input type="number" id="wallHeight" class="form-input" value="10" oninput="estimatePaint()">
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">பூச்சு வகை (Paint Type):</label>
          <select id="paintType" class="form-select" onchange="estimatePaint()">
            <option value="interior">உட்புற சுவர்கள் (Interior Emulsion)</option>
            <option value="exterior">வெளிப்புற சுவர்கள் (Weatherproof Exterior)</option>
            <option value="enamel">மர & இரும்பு எனாமல் (Enamel & Polish)</option>
          </select>
        </div>

        <div class="calc-result-box" id="aiPaintEstimate">
          <b style="font-size: 12px; color: #10B981;">🎨 தேவையான பெயிண்ட் மதிப்பீடு (200 சதுர அடி):</b>
          <div style="font-size: 11px; margin-top: 6px; color: #FFF;" id="estimateDetails">
            • வால் பிரைமர் (Primer): <b>1.8 லிட்டர்</b> (1 கோட்)<br>
            • அக்ரிலிக் பட்டி (Putty): <b>14 கிலோ</b> (2 கோட்)<br>
            • எமல்ஷன் பெயிண்ட்: <b>2.5 லிட்டர்</b> (2 கோட்)<br>
            • தோராய உழைப்பு நேரம்: <b>1 வேலை நாள்</b>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= TAB: OFFICE BEARERS (நிர்வாகிகள் அமைப்பு) ================= -->
    <section id="tab-officers" class="tab-content">
      <div class="card" style="background: linear-gradient(135deg, #2A0808, #18181B); border-left: 4px solid var(--primary);">
        <div class="card-header">
          <span class="card-title">👥 நிர்வாகிகள் (Administrators / Office Bearers)</span>
          <span class="badge">HQ மதுரை தலைமை</span>
        </div>
        <p style="font-size: 12px; color: #DDD; line-height: 1.6;">
          தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கத்தின் மாநிலம் முதல் மாவட்டம், மண்டலம், ஒன்றியம், நகரம் மற்றும் இளைஞரணி வரை அனைத்து நிர்வாகப் பொறுப்புகளின் அதிகாரப்பூர்வ பட்டியல்.
        </p>

        <!-- 8 Level Selector Buttons -->
        <div style="display: flex; gap: 6px; overflow-x: auto; padding-bottom: 6px; margin: 10px 0;">
          <button class="badge" style="cursor: pointer; background: var(--primary); color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('STATE')">🏛️ மாநில நிர்வாகிகள்</button>
          <button class="badge" style="cursor: pointer; background: #334155; color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('DISTRICT')">🏢 மாவட்ட நிர்வாகிகள்</button>
          <button class="badge" style="cursor: pointer; background: #334155; color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('ZONE')">🗺️ மண்டல நிர்வாகிகள்</button>
          <button class="badge" style="cursor: pointer; background: #334155; color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('UNION')">🌾 ஒன்றிய நிர்வாகிகள்</button>
          <button class="badge" style="cursor: pointer; background: #334155; color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('CITY')">🏙️ நகர நிர்வாகிகள்</button>
          <button class="badge" style="cursor: pointer; background: #334155; color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('DIST_YOUTH')">⚡ மாவட்ட இளைஞரணி</button>
          <button class="badge" style="cursor: pointer; background: #334155; color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('UNION_YOUTH')">🚩 ஒன்றிய இளைஞரணி</button>
          <button class="badge" style="cursor: pointer; background: #334155; color: #FFF; white-space: nowrap;" onclick="filterWebOfficers('CITY_YOUTH')">🎯 நகர இளைஞரணி</button>
        </div>

        <!-- Filter & Search Controls -->
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 8px; margin-bottom: 12px;">
          <input type="text" id="officerSearchInput" class="form-input" placeholder="🔍 பெயர், பதவி, மொபைல்..." oninput="renderWebOfficersList()" style="padding: 8px; font-size: 11px;">
          <select id="officerDistrictSelect" class="form-select" onchange="renderWebOfficersList()" style="padding: 8px; font-size: 11px;">
            <option value="ALL">அனைத்து 38 மாவட்டங்கள்</option>
            <option value="சென்னை">சென்னை (Chennai)</option>
            <option value="மதுரை">மதுரை (Madurai)</option>
            <option value="திருச்சிராப்பள்ளி">திருச்சிராப்பள்ளி (Trichy)</option>
            <option value="கோயம்புத்தூர்">கோயம்புத்தூர் (Coimbatore)</option>
            <option value="சேலம்">சேலம் (Salem)</option>
            <option value="திருநெல்வேலி">திருநெல்வேலி (Tirunelveli)</option>
            <option value="தூத்துக்குடி">தூத்துக்குடி (Thoothukudi)</option>
            <option value="ஈரோடு">ஈரோடு (Erode)</option>
            <option value="திருப்பூர்">திருப்பூர் (Tiruppur)</option>
            <option value="திண்டுக்கல்">திண்டுக்கல் (Dindigul)</option>
            <option value="தஞ்சாவூர்">தஞ்சாவூர் (Thanjavur)</option>
            <option value="வேலூர்">வேலூர் (Vellore)</option>
            <option value="கன்னியாகுமரி">கன்னியாகுமரி (Kanyakumari)</option>
            <option value="விழுப்புரம்">விழுப்புரம் (Villupuram)</option>
            <option value="கடலூர்">கடலூர் (Cuddalore)</option>
          </select>
        </div>

        <!-- Appointment Action Button for Admin -->
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
          <span style="font-size: 12px; font-weight: bold; color: var(--gold);" id="officersHeading">🏛️ மாநில நிர்வாகிகள் பட்டியல்</span>
          <button class="btn btn-gold" style="padding: 4px 10px; font-size: 10px;" onclick="document.getElementById('webAppointModal').style.display = 'block'">➕ புதிய நியமனம் (Admin)</button>
        </div>

        <!-- Bearers Container -->
        <div id="webOfficersList" style="display: flex; flex-direction: column; gap: 8px;">
          <!-- Dynamically Rendered by JavaScript -->
        </div>
      </div>

      <!-- Quick Appointment Form Modal (Web) -->
      <div id="webAppointModal" style="display: none; background: #1C1C1C; border: 2px solid var(--primary); border-radius: 12px; padding: 14px; margin-top: 14px;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
          <b style="font-size: 13px; color: var(--gold);">📝 புதிய பொறுப்பாளர் நியமனப் படிவம் (Admin)</b>
          <button onclick="document.getElementById('webAppointModal').style.display='none'" style="background:none; border:none; color:#FFF; font-size:16px; cursor:pointer;">✖</button>
        </div>
        <div class="form-group">
          <label class="form-label">நிர்வாகப் பிரிவு (Level):</label>
          <select id="newOfficerLevel" class="form-select">
            <option value="STATE">மாநில நிர்வாகிகள்</option>
            <option value="DISTRICT">மாவட்ட நிர்வாகிகள்</option>
            <option value="ZONE">மண்டல நிர்வாகிகள்</option>
            <option value="UNION">ஒன்றிய நிர்வாகிகள்</option>
            <option value="CITY">நகர நிர்வாகிகள்</option>
            <option value="DIST_YOUTH">மாவட்ட இளைஞரணி</option>
            <option value="UNION_YOUTH">ஒன்றிய இளைஞரணி</option>
            <option value="CITY_YOUTH">நகர இளைஞரணி</option>
          </select>
        </div>
        <div class="form-group">
          <label class="form-label">மாவட்டம் / பகுதி:</label>
          <select id="newOfficerDistrict" class="form-select">
            <option value="தமிழ்நாடு முழுவதும் (HQ)">தமிழ்நாடு முழுவதும் (State HQ)</option>
            <option value="மதுரை">மதுரை (Madurai)</option>
            <option value="சென்னை">சென்னை (Chennai)</option>
            <option value="திருச்சி">திருச்சி (Trichy)</option>
            <option value="கோயம்புத்தூர்">கோயம்புத்தூர் (Coimbatore)</option>
            <option value="சேலம்">சேலம் (Salem)</option>
            <option value="திருநெல்வேலி">திருநெல்வேலி (Tirunelveli)</option>
          </select>
        </div>
        <div class="form-group">
          <label class="form-label">பதவி (Designation):</label>
          <input type="text" id="newOfficerDesignation" class="form-input" placeholder="எ.கா: மாவட்டத் தலைவர் / செயலாளர்">
        </div>
        <div class="form-group">
          <label class="form-label">பொறுப்பாளர் பெயர் (தமிழ் & ஆங்கிலம்):</label>
          <input type="text" id="newOfficerName" class="form-input" placeholder="எ.கா: கே. முருகன் (K. Murugan)">
        </div>
        <div class="form-group">
          <label class="form-label">மொபைல் எண் (10 இலக்கம்):</label>
          <input type="tel" id="newOfficerPhone" class="form-input" placeholder="98421XXXXX">
        </div>
        <button class="btn btn-primary" style="width: 100%; margin-top: 8px;" onclick="saveWebNewOfficer()">💾 நியமனத்தை உறுதி செய்க (Save Appointment)</button>
      </div>
    </section>

    <!-- ================= TAB 7: CONTACTS & DIRECTORY ================= -->
    <section id="tab-contact" class="tab-content">
      <div class="card">
        <div class="card-header">
          <span class="card-title">📍 மாநில தலைமை அலுவலகம் & அவசர தொடர்பு</span>
        </div>
        <p style="font-size: 12px; color: #DDD; line-height: 1.6;">
          🏢 <b>மாநில தலைமை அலுவலகம்:</b> 1/14, அம்பலக்காரன் பட்டி, உத்தங்குடி, மேலூர் மெயின் ரோடு, மதுரை - 625107<br>
          📞 <b>தலைமை தொடர்பு:</b> +91 97893 31681 (மாநிலத் தலைவர் எஸ். மைக்கேல் ஆல்வின்) / +91 70101 31915 (பொதுச் செயலாளர் சேவியர் பாபு)<br>
          ✉️ <b>மின்னஞ்சல்:</b> contact@tnpa.org / xavierbabu2@gmail.com<br>
          🌐 <b>அதிகாரப்பூர்வ வலைத்தளம்:</b> https://tnpa.org
        </p>
        <div style="margin-top: 12px; display: grid; grid-template-columns: 1fr 1fr; gap: 8px;">
          <button class="btn btn-green" onclick="window.location.href='tel:+917010131915'">📞 நேரடி அழைப்பு (Call)</button>
          <button class="btn btn-gold" onclick="window.open('https://api.whatsapp.com/send?phone=917010131915', '_blank')">💬 வாட்ஸ்அப் உதவி</button>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <span class="card-title">🏛️ முக்கிய மாவட்ட செயலாளர்கள்</span>
        </div>
        <div style="font-size: 11px; color: #DDD; line-height: 1.8;">
          • <b>திருச்சி:</b> கே. சுந்தர்ராஜ் (மாவட்ட தலைவர்) - 944321XXXX<br>
          • <b>சென்னை:</b> எம். இளங்கோவன் (மாவட்ட செயலாளர்) - 984102XXXX<br>
          • <b>மதுரை:</b> பி. செல்லத்துரை (செயலாளர்) - 989410XXXX<br>
          • <b>கோவை:</b> வி. பழனிச்சாமி (பொருளாளர்) - 944355XXXX
        </div>
      </div>
    </section>

  </div>

  <!-- Bottom Navigation Bar -->
  <div class="bottom-nav">
    <div class="bottom-item active" onclick="switchTab('tab-home')">
      <span>🏠</span>
      <span>முகப்பு</span>
    </div>
    <div class="bottom-item" onclick="switchTab('tab-register')">
      <span>🪪</span>
      <span>ID அட்டை</span>
    </div>
    <div class="bottom-item" onclick="switchTab('tab-live')">
      <span>📺</span>
      <span>Live TV</span>
    </div>
    <div class="bottom-item" onclick="switchTab('tab-welfare')">
      <span>⚖️</span>
      <span>நலவாரியம்</span>
    </div>
    <div class="bottom-item" onclick="switchTab('tab-contact')">
      <span>📞</span>
      <span>தொடர்பு</span>
    </div>
  </div>

  <!-- Interactive JavaScript Engine -->
  <script>
    // Tab Switching Engine
    function switchTab(tabId) {
      document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
      document.querySelectorAll('.nav-tab-btn').forEach(el => el.classList.remove('active'));
      
      const target = document.getElementById(tabId);
      if (target) target.classList.add('active');

      const btns = document.querySelectorAll('.nav-tab-btn');
      btns.forEach(b => {
        if (b.getAttribute('onclick') && b.getAttribute('onclick').includes(tabId)) {
          b.classList.add('active');
        }
      });
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    // ID Card Generator
    function generateWebIdCard() {
      const name = document.getElementById('regName').value || 'Muthukumar';
      const age = document.getElementById('regAge').value || '32';
      const blood = document.getElementById('regBlood').value || 'B+ve';
      const exp = document.getElementById('regExp').value || '10';
      const dist = document.getElementById('regDistrict').value || 'Trichy';
      const trade = document.getElementById('regTrade').value || 'Wall Painting';

      document.getElementById('cardNameVal').innerText = name;
      document.getElementById('cardAgeVal').innerText = age + ' வயது • ' + blood;
      document.getElementById('cardExpVal').innerText = exp + ' ஆண்டுகள்';
      document.getElementById('cardDistVal').innerText = dist;
      document.getElementById('cardTradeVal').innerText = trade;
      document.getElementById('cardIdVal').innerText = 'TNPA-2026-' + Math.floor(1000 + Math.random() * 9000);

      document.getElementById('idCardResult').style.display = 'block';
      document.getElementById('idCardResult').scrollIntoView({ behavior: 'smooth' });
    }

    // Live TV Canvas Animation Simulator
    const liveCanvas = document.getElementById('liveTvCanvas');
    let isLivePlaying = true;
    let liveAnimId;
    let lastRenderTime = 0;

    if (liveCanvas) {
      const ctx = liveCanvas.getContext('2d');
      let angle = 0;

      function renderLiveTv(timestamp) {
        if (!isLivePlaying) return;
        
        // Throttle rendering to ~20 FPS for smooth performance and low resource usage
        if (timestamp - lastRenderTime > 50) {
          lastRenderTime = timestamp;
          
          ctx.fillStyle = '#111';
          ctx.fillRect(0, 0, liveCanvas.width, liveCanvas.height);

          // Draw animated gradient studio background
          const grad = ctx.createLinearGradient(0, 0, liveCanvas.width, liveCanvas.height);
          grad.addColorStop(0, '#990000');
          grad.addColorStop(0.5, '#E60000');
          grad.addColorStop(1, '#000000');
          ctx.fillStyle = grad;
          ctx.fillRect(0, 0, liveCanvas.width, liveCanvas.height);

          // Animated color paint strokes
          ctx.strokeStyle = '#FFD700';
          ctx.lineWidth = 3;
          ctx.beginPath();
          for (let x = 0; x < liveCanvas.width; x += 15) {
            const y = 100 + Math.sin((x + angle) * 0.05) * 15;
            if (x === 0) ctx.moveTo(x, y);
            else ctx.lineTo(x, y);
          }
          ctx.stroke();

          // Broadcast title text
          ctx.fillStyle = '#FFFFFF';
          ctx.font = 'bold 14px Mukta Malar, sans-serif';
          ctx.fillText('TNPA² Live TV • வண்ணக் கலவை செயல்முறை', 20, 40);

          ctx.fillStyle = '#FFD700';
          ctx.font = '11px sans-serif';
          ctx.fillText('🔴 LIVE STREAM • 1080p • HLS MASTER', 20, 60);

          angle += 3;
        }
        liveAnimId = requestAnimationFrame(renderLiveTv);
      }
      liveAnimId = requestAnimationFrame(renderLiveTv);
    }

    function toggleLivePlay() {
      isLivePlaying = !isLivePlaying;
      document.getElementById('playBtnText').innerText = isLivePlaying ? '⏸ இடைநிறுத்து' : '▶️ இயக்கு';
      if (isLivePlaying) {
        liveAnimId = requestAnimationFrame(renderLiveTv);
      }
    }

    function sendWebChat() {
      const input = document.getElementById('chatInput');
      if (!input.value.trim()) return;
      const box = document.getElementById('chatMessages');
      const p = document.createElement('p');
      p.innerHTML = '<b>நீங்கள்:</b> ' + input.value;
      box.appendChild(p);
      input.value = '';
      box.scrollTop = box.scrollHeight;
    }

    function likeArt(btn) {
      let count = parseInt(btn.innerText.replace(/[^0-9]/g, '')) || 0;
      count++;
      btn.innerText = '❤️ ' + count + ' Likes';
      btn.style.borderColor = '#E60000';
      btn.style.color = '#FF4D4D';
    }

    function generateWebIdCard() {
      const name = document.getElementById('regName').value.trim() || 'உறுப்பினர்';
      const age = document.getElementById('regAge').value.trim() || '32';
      const blood = document.getElementById('regBlood').value;
      const exp = document.getElementById('regExp').value.trim() || '8';
      const dist = document.getElementById('regDistrict').value;
      const trade = document.getElementById('regTrade').value;
      const phone = document.getElementById('regPhone').value.trim() || '9876543210';
      const randomId = 'TNPA-2026-' + Math.floor(1000 + Math.random() * 9000);

      document.getElementById('cardNameVal').innerText = name;
      document.getElementById('cardIdVal').innerText = randomId;
      document.getElementById('cardAgeVal').innerText = age + ' வயது • ' + blood;
      document.getElementById('cardExpVal').innerText = exp + ' ஆண்டுகள்';
      document.getElementById('cardTradeVal').innerText = trade;
      document.getElementById('cardDistVal').innerText = dist;

      document.getElementById('idCardResult').style.display = 'block';
      document.getElementById('idCardResult').scrollIntoView({ behavior: 'smooth' });
    }

    function shareWebCard() {
      const name = document.getElementById('cardNameVal').innerText;
      const id = document.getElementById('cardIdVal').innerText;
      const dist = document.getElementById('cardDistVal').innerText;
      const trade = document.getElementById('cardTradeVal').innerText;
      const text = encodeURIComponent('🏛️ TNPA டிஜிட்டல் உறுப்பினர் அடையாள அட்டை\nஉறுப்பினர் எண்: ' + id + '\nபெயர்: ' + name + '\nமாவட்டம்: ' + dist + '\nதொழில்: ' + trade + '\nதமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் சங்கம்');
      window.open('https://api.whatsapp.com/send?text=' + text, '_blank');
    }

    function calculatePension() {
      const age = parseInt(document.getElementById('calcAge').value) || 38;
      const yrs = parseInt(document.getElementById('calcYears').value) || 10;
      const rem = Math.max(0, 60 - age);
      document.getElementById('calcOutput').innerHTML = 
        '<b style="font-size: 12px; color: #10B981;">📋 உங்கள் நலவாரிய உரிமை மதிப்பீடு:</b>' +
        '<div style="font-size: 11px; margin-top: 6px; color: #FFF;">' +
        '• இன்னும் <b>' + rem + ' ஆண்டுகளில்</b> முழு ஓய்வூதியம் ₹1,000/மாதம் துவங்கும்.<br>' +
        '• சங்க உறுப்பினர் முதிர்வு நிதி & விபத்து காப்பீடு: <b>₹5,00,000</b><br>' +
        '• திருமண நிதியுதவி: <b>₹20,000</b> | கல்வி உதவி: <b>₹4,000 - ₹8,000</b></div>';
    }

    function estimatePaint() {
      const len = parseFloat(document.getElementById('wallLength').value) || 20;
      const hgt = parseFloat(document.getElementById('wallHeight').value) || 10;
      const sqft = len * hgt;
      const primer = (sqft / 110).toFixed(1);
      const putty = (sqft * 0.07).toFixed(1);
      const emulsion = (sqft / 80).toFixed(1);

      document.getElementById('aiPaintEstimate').innerHTML = 
        '<b style="font-size: 12px; color: #10B981;">🎨 தேவையான பெயிண்ட் மதிப்பீடு (' + sqft + ' சதுர அடி):</b>' +
        '<div style="font-size: 11px; margin-top: 6px; color: #FFF;">' +
        '• வால் பிரைமர் (Primer): <b>' + primer + ' லிட்டர்</b><br>' +
        '• அக்ரிலிக் பட்டி (Putty): <b>' + putty + ' கிலோ</b><br>' +
        '• எமல்ஷன் பெயிண்ட்: <b>' + emulsion + ' லிட்டர்</b> (2 கோட்)<br>' +
        '• மதிப்பீட்டு உழைப்பு நேரம்: <b>' + (sqft > 300 ? '2 நாட்கள்' : '1 நாள்') + '</b></div>';
    }

    // Web Office Bearers Management Engine
    let currentOfficerLevel = 'STATE';
    let webOfficersData = [
      { id: 'TNPA-OB-001', name: 'எஸ். மைக்கேல் ஆல்வின் (S. Michael Alvin)', role: 'மாநிலத் தலைவர் (Super Admin)', level: 'STATE', dist: 'மதுரை மாவட்டம் (HQ)', phone: '9789331681' },
      { id: 'TNPA-OB-002', name: 'சேவியர் பாபு (Xavier Babu)', role: 'மாநில பொதுச் செயலாளர் (Super Admin)', level: 'STATE', dist: 'மதுரை மாவட்டம் (HQ)', phone: '7010131915' },
      { id: 'TNPA-OB-003', name: 'சக்திவேல் (Sakthivel)', role: 'மாநில பொருளாளர் (Treasurer)', level: 'STATE', dist: 'திருச்சிராப்பள்ளி (HQ)', phone: '9080047281' }
    ];

    function filterWebOfficers(lvl) {
      currentOfficerLevel = lvl;
      const titles = {
        'STATE': '🏛️ மாநில நிர்வாகிகள் பட்டியல்',
        'DISTRICT': '🏢 மாவட்ட நிர்வாகிகள் பட்டியல்',
        'ZONE': '🗺️ மண்டல நிர்வாகிகள் பட்டியல்',
        'UNION': '🌾 ஒன்றிய நிர்வாகிகள் பட்டியல்',
        'CITY': '🏙️ நகர நிர்வாகிகள் பட்டியல்',
        'DIST_YOUTH': '⚡ மாவட்ட இளைஞரணி நிர்வாகிகள்',
        'UNION_YOUTH': '🚩 ஒன்றிய இளைஞரணி நிர்வாகிகள்',
        'CITY_YOUTH': '🎯 நகர இளைஞரணி நிர்வாகிகள்'
      };
      document.getElementById('officersHeading').innerText = titles[lvl] || 'நிர்வாகிகள் பட்டியல்';
      renderWebOfficersList();
    }

    function renderWebOfficersList() {
      const listEl = document.getElementById('webOfficersList');
      if (!listEl) return;

      const q = (document.getElementById('officerSearchInput') ? document.getElementById('officerSearchInput').value.toLowerCase().trim() : '');
      const distFilter = (document.getElementById('officerDistrictSelect') ? document.getElementById('officerDistrictSelect').value : 'ALL');

      const filtered = webOfficersData.filter(o => {
        const matchLvl = o.level === currentOfficerLevel;
        const matchDist = distFilter === 'ALL' || o.dist.includes(distFilter);
        const matchQ = !q || o.name.toLowerCase().includes(q) || o.role.toLowerCase().includes(q) || o.phone.includes(q);
        return matchLvl && matchDist && matchQ;
      });

      if (filtered.length === 0) {
        listEl.innerHTML = '<div style="padding: 16px; text-align: center; background: #1E293B; border-radius: 8px; font-size: 11px; color: #94A3B8;">ℹ️ இப்பிரிவில் அல்லது தேர்ந்தெடுக்கப்பட்ட மாவட்டத்தில் இன்னும் பொறுப்பாளர்கள் நியமிக்கப்படவில்லை.<br><span style="color: var(--gold);">மேலே உள்ள "➕ புதிய நியமனம்" மூலம் நிர்வாகிகளை நியமிக்கலாம்.</span></div>';
        return;
      }

      listEl.innerHTML = filtered.map(function(o) {
        return '<div style="background: #18181B; border: 1px solid #334155; border-radius: 8px; padding: 10px; display: flex; justify-content: space-between; align-items: center;">' +
          '<div>' +
            '<div style="font-weight: 900; font-size: 13px; color: #FFF;">' + o.name + '</div>' +
            '<div style="font-size: 11px; color: var(--gold); font-weight: bold; margin-top: 2px;">' + o.role + '</div>' +
            '<div style="font-size: 10px; color: #94A3B8; margin-top: 2px;">📍 ' + o.dist + ' • ID: ' + o.id + '</div>' +
          '</div>' +
          '<div style="display: flex; flex-direction: column; gap: 4px;">' +
            '<button class="btn btn-green" style="padding: 4px 8px; font-size: 10px;" onclick="window.location.href=\'tel:+91' + o.phone + '\'">📞 அழைக்க</button>' +
            '<button class="btn btn-gold" style="padding: 4px 8px; font-size: 10px;" onclick="window.open(\'https://api.whatsapp.com/send?phone=91' + o.phone + '\', \'_blank\')">💬 WhatsApp</button>' +
          '</div>' +
        '</div>';
      }).join('');
    }

    function saveWebNewOfficer() {
      const lvl = document.getElementById('newOfficerLevel').value;
      const dist = document.getElementById('newOfficerDistrict').value;
      const role = document.getElementById('newOfficerDesignation').value.trim() || 'பொறுப்பாளர்';
      const name = document.getElementById('newOfficerName').value.trim();
      const phone = document.getElementById('newOfficerPhone').value.trim() || '9842100000';

      if (!name) {
        alert('பொறுப்பாளர் பெயரை உள்ளிடவும்.');
        return;
      }

      webOfficersData.unshift({
        id: 'TNPA-OB-' + Math.floor(100 + Math.random() * 900),
        name: name,
        role: role,
        level: lvl,
        dist: dist,
        phone: phone
      });

      document.getElementById('webAppointModal').style.display = 'none';
      alert('✅ புதிய பொறுப்பாளர் ' + name + ' வெற்றிகரமாக நியமிக்கப்பட்டார்!');
      currentOfficerLevel = lvl;
      filterWebOfficers(lvl);
    }

    // Initial Render
    setTimeout(renderWebOfficersList, 300);
  </script>
</body>
</html>"""
  }
}
