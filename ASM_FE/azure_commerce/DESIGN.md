# Design System Specification: The Precision Editorial

This design system is a high-end, editorial-inspired framework crafted for modern e-commerce. It moves away from the "template" aesthetic of generic online shops, instead favoring a sophisticated, layered approach that emphasizes authority, trust, and rhythmic white space.

## 1. Creative North Star: The Digital Curator
The "Digital Curator" philosophy treats every interface as a premium gallery space. We do not "box" content; we "mount" it. By leveraging intentional asymmetry, varying typographic scales, and tonal depth, we create an experience that feels bespoke and high-trust. We avoid the rigid, "grid-locked" look of standard e-commerce by allowing elements to breathe and overlap, creating a sense of physical layering rather than a flat digital screen.

---

## 2. Color Strategy & Surface Logic
The palette is rooted in a deep, authoritative Blue and a professional Slate, balanced by functional Success and Error tones. However, the soul of the system lies in how we treat white space and surfaces.

### The "No-Line" Rule
**Explicit Instruction:** Do not use 1px solid borders to define sections or containers. Visual boundaries must be achieved exclusively through background color shifts. Use `surface-container-low` (#eff4ff) to define a section against the main `surface` (#f8f9ff). Borders create "visual noise" that cheapens the experience; tonal shifts create "atmosphere."

### Surface Hierarchy & Nesting
Treat the UI as a series of stacked, premium materials.
- **Base Layer:** `surface` (#f8f9ff)
- **Secondary Sectioning:** `surface-container-low` (#eff4ff)
- **High-Emphasis Cards:** `surface-container-lowest` (#ffffff) sitting on a lower-tier background.
- **Active/Interactive Overlays:** `surface-container-highest` (#d3e4fe)

### Signature Textures & Glassmorphism
To add "soul" to the professional blue, use subtle linear gradients for primary CTAs, transitioning from `primary` (#004ac6) to `primary_container` (#2563eb) at a 135-degree angle. For floating navigation or product filters, apply a **Glassmorphic effect**:
- **Background:** `surface` at 70% opacity.
- **Effect:** `backdrop-blur: 12px`.
- **Result:** Content bleeds through softly, making the UI feel integrated and premium.

---

## 3. Typography: The Editorial Voice
We use a dual-sans-serif approach to create a distinction between "Expression" (Brand) and "Utility" (Interface).

*   **Display & Headlines (Manrope):** Our "Expression" font. Use `display-lg` (3.5rem) and `headline-md` (1.75rem) to create high-contrast editorial moments. Manrope’s geometric nature provides a modern, structural feel.
*   **Body & Labels (Inter):** Our "Utility" font. Inter provides maximum legibility for product descriptions and administrative data.
*   **Hierarchy as Authority:** Use `on_surface_variant` (#434655) for secondary metadata to ensure the user’s eye always lands on the `on_surface` (#0b1c30) primary information first.

---

## 4. Elevation & Depth: Tonal Layering
Traditional drop shadows are often a crutch for poor layout. In this system, we prioritize **Tonal Layering**.

*   **The Layering Principle:** Depth is achieved by "stacking" tiers. A product card (`surface-container-lowest`) placed on a category background (`surface-container-low`) creates a natural lift.
*   **Ambient Shadows:** If a floating element (like a Cart Modal) requires a shadow, use a large blur (24px to 40px) at a very low 4% opacity. The shadow color must be a tint of `on_surface` (#0b1c30), not pure black.
*   **The Ghost Border Fallback:** If a border is required for accessibility (e.g., in high-contrast scenarios), use `outline_variant` (#c3c6d7) at **15% opacity**. Never use 100% opaque borders.

---

## 5. Component Logic

### Buttons
*   **Primary:** Gradient of `primary` to `primary_container`. `DEFAULT` (8px) roundedness. Use `on_primary` (#ffffff) for text.
*   **Secondary:** `surface_container_highest` background with `primary` text. No border.
*   **Tertiary:** Ghost style. No background; `primary` text. Transitions to `surface_container_low` on hover.

### Product Cards
*   **Structure:** No borders. Use `surface-container-lowest` (#ffffff) for the card body.
*   **Spacing:** Use `spacing-4` (1rem) for internal padding.
*   **Shadows:** Apply a 2px "Soft Bloom" shadow only on hover to indicate interactivity.

### Administrative Tables
*   **Separation:** Forbid the use of horizontal divider lines. Instead, use alternating row tints: `surface` and `surface-container-low`.
*   **Typography:** Use `label-md` for headers in all-caps with 0.05em letter spacing to provide a professional, data-heavy feel.

### Input Fields
*   **State:** Default state uses `surface-container-highest` background. No border.
*   **Focus State:** A 2px "Ghost Border" using `primary` at 40% opacity. This maintains the clean look while providing clear affordance.

---

## 6. Do’s and Don'ts

### Do:
*   **Use White Space as a Separator:** Use `spacing-12` (3rem) or `spacing-16` (4rem) between major sections rather than a line.
*   **Embrace Asymmetry:** In hero sections, align text to the left and offset product imagery to the right, slightly overlapping the section boundary for a custom feel.
*   **Standardize Radius:** Maintain the `DEFAULT` (0.5rem / 8px) radius across all cards and buttons to ensure a cohesive visual language.

### Don't:
*   **Don't use 1px solid Grey lines:** This is the quickest way to make a high-end system look like a generic template.
*   **Don't use pure black (#000000):** Use `on_surface` (#0b1c30) for text to maintain a softer, more sophisticated contrast.
*   **Don't crowd components:** If a layout feels "busy," increase the spacing scale rather than adding borders or boxes.