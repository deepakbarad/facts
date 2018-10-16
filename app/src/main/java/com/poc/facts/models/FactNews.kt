package com.poc.facts.models

import org.parceler.Parcel

@Parcel
class FactNews
{
    var title:String = "";
    lateinit var rows:List<Fact>;
}