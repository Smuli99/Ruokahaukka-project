package com.ruokkis.client

import com.ruokkis.models.NewDayMenu
import com.ruokkis.models.NewFood
import com.ruokkis.models.NewMenuItem
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.NewWeekMenu
import kotlinx.datetime.LocalDate

val jsonResponse = """
    {
      "RestaurantName": "Ravintola Ilokivi",
      "RestaurantUrl": "https://www.semma.fi/ravintolat/seminaarinmaki/ilokivi/",
      "PriceHeader": null,
      "Footer": null,
      "MenusForDays": [
        {
          "Date": "2026-11-02T00:00:00+00:00",
          "LunchTime": "10:45–16:00",
          "SetMenus": [
            {
              "SortOrder": 5,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Porkkanaohukaisia (L, KELA)",
                "Puolukkahilloa (G, Veg)",
                "Perunasosetta (L, G)"
              ]
            },
            {
              "SortOrder": 7,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Porkkanalettuja (gluteeniton, vegaaninen) (M, G, KELA)",
                "Puolukkahilloa (G, Veg)",
                "Perunasosetta (vegaaninen) (M, G, Veg)"
              ]
            },
            {
              "SortOrder": 9,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Rapeita kalapaloja MSC (M, KELA)",
                "Sitruunakastiketta (L, KELA)",
                "Perunasosetta (L, G)"
              ]
            },
            {
              "SortOrder": 10,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Butter Chicken (L, G, KELA)",
                "Riisiä (G, Veg)"
              ]
            },
            {
              "SortOrder": 12,
              "Name": "Gluteeniton lounas 1",
              "Price": null,
              "Components": [
                "Rapeita kalapaloja (gluteeniton) (M, G)",
                "Sitruunakastiketta (gluteeniton, maidoton) (M, G, KELA)",
                "Perunasosetta (maidoton) (M, G, Veg)"
              ]
            }
          ]
        },
        {
          "Date": "2026-11-03T00:00:00+00:00",
          "LunchTime": "10:45–16:00",
          "SetMenus": [
            {
              "SortOrder": 5,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Riisipuuroa (L, G, KELA)"
              ]
            },
            {
              "SortOrder": 7,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Riisipuuroa (vegaaninen, gluteeniton) (M, G, Veg, KELA)"
              ]
            },
            {
              "SortOrder": 9,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Jauhelihapastavuokaa (M, KELA)"
              ]
            },
            {
              "SortOrder": 10,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Pesto-kanakastiketta (L, G)",
                "Riisiä (G, Veg)"
              ]
            },
            {
              "SortOrder": 12,
              "Name": "Gluteeniton lounas 1",
              "Price": null,
              "Components": [
                "Jauhelihapastavuokaa (gluteeniton) (M, G, KELA)"
              ]
            }
          ]
        },
        {
          "Date": "2026-11-04T00:00:00+00:00",
          "LunchTime": "10:45–16:00",
          "SetMenus": [
            {
              "SortOrder": 5,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Kasvispaellaa (M, G, Veg, KELA)"
              ]
            },
            {
              "SortOrder": 9,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Ilokiven kalamurekepihvi (L, KELA)",
                "Kermaista tillikastiketta (L, KELA)",
                "Perunaa (G, Veg)"
              ]
            },
            {
              "SortOrder": 10,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Minilihapyöryköitä ja tulista tomaattikastiketta (M, G)",
                "Cappelli pastaa (Veg, KELA)"
              ]
            },
            {
              "SortOrder": 12,
              "Name": "Gluteeniton lounas 1",
              "Price": null,
              "Components": [
                "Ilokiven kalamurekepihvi (gluteeniton, maidoton) (M, G, KELA)",
                "Tillikastiketta (gluteeniton, maidoton) (M, G, KELA)"
              ]
            },
            {
              "SortOrder": 13,
              "Name": "Gluteeniton lounas 2",
              "Price": null,
              "Components": [
                "Minilihapyöryköitä ja tulista tomaattikastiketta (M, G)",
                "Fusilli pastaa (gluteeniton) (M, G, Veg)"
              ]
            }
          ]
        },
        {
          "Date": "2026-11-05T00:00:00+00:00",
          "LunchTime": "10:45–16:00",
          "SetMenus": [
            {
              "SortOrder": 5,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Kasviskepukoita (G, Veg, KELA)",
                "Tomaatti-timjamikastiketta (sipulijauheella) (M, Veg, KELA)",
                "Perunaa (G, Veg)"
              ]
            },
            {
              "SortOrder": 9,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Palermon kanapastaa (L, KELA)",
                "Pastaa JBA korispelaajille! ()"
              ]
            },
            {
              "SortOrder": 10,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Poro-pekonikiusausta (L, G)"
              ]
            },
            {
              "SortOrder": 12,
              "Name": "Gluteeniton lounas 1",
              "Price": null,
              "Components": [
                "Palermon kanapastaa (gluteeniton) (M, G, KELA)"
              ]
            }
          ]
        },
        {
          "Date": "2026-11-06T00:00:00+00:00",
          "LunchTime": "10:45–16:00",
          "SetMenus": [
            {
              "SortOrder": 5,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Kookos-tofukorma (G, Veg, KELA)",
                "Riisiä (G, Veg)"
              ]
            },
            {
              "SortOrder": 9,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Nyhtöpossupastaa (L, KELA)"
              ]
            },
            {
              "SortOrder": 10,
              "Name": "Lounas",
              "Price": null,
              "Components": [
                "Kanapuikkoja (M)",
                "Kylmää savupaprikakastiketta (L, G)",
                "Kuorellisia paahdettuja perunoita (G, Veg)"
              ]
            },
            {
              "SortOrder": 12,
              "Name": "Gluteeniton lounas 1",
              "Price": null,
              "Components": [
                "Kanapyöryköitä (gluteeniton) (M, G)",
                "Kylmää savupaprikakastiketta (maidoton) (M, G, Veg, ILM)"
              ]
            }
          ]
        }
      ],
      "ErrorText": null
    }
""".trimIndent()

private fun food(name: String, vararg allergies: String) =
    NewFood(name = name, allergies = allergies.toList())

private fun item(sortOrder: Int, info: String, vararg foods: NewFood) =
    NewMenuItem(price = null, info = info, sortOrder = sortOrder, foods = foods.toList())

val expectedResult = NewRestaurantMenu(
    name = "Ilokivi",
    url = "https://www.semma.fi/ravintolat/seminaarinmaki/ilokivi/",
    campus = "Seminaarinmäki",
    weekMenu = NewWeekMenu(
        weekStart = LocalDate(2026, 11, 2),
        days = listOf(
            NewDayMenu(
                date = LocalDate(2026, 11, 2),
                weekday = "ma",
                lunchTime = "10:45–16:00",
                items = listOf(
                    item(5, "Lounas",
                        food("Porkkanaohukaisia", "L", "KELA"),
                        food("Puolukkahilloa", "G", "Veg"),
                        food("Perunasosetta", "L", "G"),
                    ),
                    item(7, "Lounas",
                        food("Porkkanalettuja", "M", "G", "KELA"),
                        food("Puolukkahilloa", "G", "Veg"),
                        food("Perunasosetta", "M", "G", "Veg"),
                    ),
                    item(9, "Lounas",
                        food("Rapeita kalapaloja MSC", "M", "KELA"),
                        food("Sitruunakastiketta", "L", "KELA"),
                        food("Perunasosetta", "L", "G"),
                    ),
                    item(10, "Lounas",
                        food("Butter Chicken", "L", "G", "KELA"),
                        food("Riisiä", "G", "Veg"),
                    ),
                    item(12, "Gluteeniton lounas 1",
                        food("Rapeita kalapaloja", "M", "G"),
                        food("Sitruunakastiketta", "M", "G", "KELA"),
                        food("Perunasosetta", "M", "G", "Veg"),
                    ),
                )
            ),
            NewDayMenu(
                date = LocalDate(2026, 11, 3),
                weekday = "ti",
                lunchTime = "10:45–16:00",
                items = listOf(
                    item(5, "Lounas",
                        food("Riisipuuroa", "L", "G", "KELA"),
                    ),
                    item(7, "Lounas",
                        food("Riisipuuroa", "M", "G", "Veg", "KELA"),
                    ),
                    item(9, "Lounas",
                        food("Jauhelihapastavuokaa", "M", "KELA"),
                    ),
                    item(10, "Lounas",
                        food("Pesto-kanakastiketta", "L", "G"),
                        food("Riisiä", "G", "Veg"),
                    ),
                    item(12, "Gluteeniton lounas 1",
                        food("Jauhelihapastavuokaa", "M", "G", "KELA"),
                    ),
                )
            ),
            NewDayMenu(
                date = LocalDate(2026, 11, 4),
                weekday = "ke",
                lunchTime = "10:45–16:00",
                items = listOf(
                    item(5, "Lounas",
                        food("Kasvispaellaa", "M", "G", "Veg", "KELA"),
                    ),
                    item(9, "Lounas",
                        food("Ilokiven kalamurekepihvi", "L", "KELA"),
                        food("Kermaista tillikastiketta", "L", "KELA"),
                        food("Perunaa", "G", "Veg"),
                    ),
                    item(10, "Lounas",
                        food("Minilihapyöryköitä ja tulista tomaattikastiketta", "M", "G"),
                        food("Cappelli pastaa", "Veg", "KELA"),
                    ),
                    item(12, "Gluteeniton lounas 1",
                        food("Ilokiven kalamurekepihvi", "M", "G", "KELA"),
                        food("Tillikastiketta", "M", "G", "KELA"),
                    ),
                    item(13, "Gluteeniton lounas 2",
                        food("Minilihapyöryköitä ja tulista tomaattikastiketta", "M", "G"),
                        food("Fusilli pastaa", "M", "G", "Veg"),
                    ),
                )
            ),
            NewDayMenu(
                date = LocalDate(2026, 11, 5),
                weekday = "to",
                lunchTime = "10:45–16:00",
                items = listOf(
                    item(5, "Lounas",
                        food("Kasviskepukoita", "G", "Veg", "KELA"),
                        food("Tomaatti-timjamikastiketta", "M", "Veg", "KELA"),
                        food("Perunaa", "G", "Veg"),
                    ),
                    item(9, "Lounas",
                        food("Palermon kanapastaa", "L", "KELA"),
                        food("Pastaa JBA korispelaajille!"),
                    ),
                    item(10, "Lounas",
                        food("Poro-pekonikiusausta", "L", "G"),
                    ),
                    item(12, "Gluteeniton lounas 1",
                        food("Palermon kanapastaa", "M", "G", "KELA"),
                    ),
                )
            ),
            NewDayMenu(
                date = LocalDate(2026, 11, 6),
                weekday = "pe",
                lunchTime = "10:45–16:00",
                items = listOf(
                    item(5, "Lounas",
                        food("Kookos-tofukorma", "G", "Veg", "KELA"),
                        food("Riisiä", "G", "Veg"),
                    ),
                    item(9, "Lounas",
                        food("Nyhtöpossupastaa", "L", "KELA"),
                    ),
                    item(10, "Lounas",
                        food("Kanapuikkoja", "M"),
                        food("Kylmää savupaprikakastiketta", "L", "G"),
                        food("Kuorellisia paahdettuja perunoita", "G", "Veg"),
                    ),
                    item(12, "Gluteeniton lounas 1",
                        food("Kanapyöryköitä", "M", "G"),
                        food("Kylmää savupaprikakastiketta", "M", "G", "Veg", "ILM"),
                    ),
                )
            ),
        )
    )
)