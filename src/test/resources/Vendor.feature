Feature: Vendors
  Vendors represent tourism businesses/attractions that offer services for which passes can be purchased

  Scenario Outline: Adding a new vendor
    I can add a vendor which does not already exist to the system

    When I add a new <Vendor>
    Then I am given their unique  <Vendor ID>

    Examples:
      | Vendor         | Vendor ID     |
      | Thorpe Park    | thorpepark    |
      | Alton Towers   | altontowers   |
      | London Dungeon | londondungeon |

  Scenario Outline: Adding a vendor which already exists
    If a vendor already exists in the system, subsequent calls to add that vendor should not fail, but instead
    return the data for the vendor

    Given The <Vendor> already exists in the system
    When I add a new <Vendor>
    Then I am given their unique <Vendor ID>

    Examples:
      | Vendor         | Vendor ID     |
      | Thorpe Park    | thorpepark    |
      | Alton Towers   | altontowers   |
      | London Dungeon | londondungeon |