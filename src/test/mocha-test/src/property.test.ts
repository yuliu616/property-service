import { expect } from 'chai';
import { default as axios } from 'axios';

import {
  apiBaseUrl,
  base64Pattern,
  dateTimeAndMsNoZonePattern,
  dateTimeNoZonePattern,
  localDatePattern,
  numberOnlyPattern,
  generalLettersPattern,
  alphanumericOrChinese,
} from './common.test';

beforeEach(async function(){
  await axios.post(`${apiBaseUrl}/debug/enableAllProperties`);
  return await axios.post(`${apiBaseUrl}/debug/resetVersionOfAllProperties`);
});

describe('property', function(){

  it('could create new property', async function(){
    let data = {
      "name": "宋皇酒店",
      "ownerId": "33030",
      "ownershipDate": "2020-12-25",
      "geoAddrLatitude": 30.2788218671027600,
      "geoAddrLongitude": 120.1631599554121500,
      "addrFloorLine": "宋皇酒店",
      "addrBlock": '3栋',
      "addrStreetAddress": "大家乐社区 朝晖路333号 宋皇酒店",
      "addrDistrict": "东城区",
      "addrArea": "西西街道",
      "addrPostalCode": "100033",
      "addrCity": "杭州",
      "addrProvince": "江浙",
      "addrState": "无",
      "addrCountryCode": "cn",
      "active": true
    };

    // invoke to create property
    let res = await axios.post(`${apiBaseUrl}/property`, data);
    expect(res.data).is.an('object');
    expect(res.data.id).to.match(numberOnlyPattern).that.exist;
    expect(res.data.version).at.least(1);
    expect(res.data.creationDate).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.lastUpdated).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.name).eq('宋皇酒店');
    expect(res.data.ownerId).eq('33030');
    expect(res.data.ownershipDate).eq('2020-12-25');
    expect(res.data.geoAddrLatitude).eq(30.2788218671027600);
    expect(res.data.geoAddrLongitude).eq(120.1631599554121500);
    expect(res.data.addrFloorLine).eq('宋皇酒店');
    expect(res.data.addrBlock).eq('3栋');
    expect(res.data.addrStreetAddress).eq('大家乐社区 朝晖路333号 宋皇酒店');
    expect(res.data.addrDistrict).eq('东城区');
    expect(res.data.addrArea).eq('西西街道');
    expect(res.data.addrPostalCode).eq('100033');
    expect(res.data.addrCity).eq('杭州');
    expect(res.data.addrProvince).eq('江浙');
    expect(res.data.addrState).eq('无');
    expect(res.data.addrCountryCode).eq('cn');
    expect(res.data.active).eq(true);
  });

  it('could find target record by id', async function(){
    // query target property
    let res = await axios.get(`${apiBaseUrl}/property/24009`);
    expect(res.data).is.an('object');
    expect(res.data.id).to.match(numberOnlyPattern).that.exist;
    expect(res.data.version).at.least(1);
    expect(res.data.creationDate).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.lastUpdated).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.name).eq('汉庭酒店');
    expect(res.data.ownerId).eq('33014');
    expect(res.data.ownershipDate).eq('2020-05-20');
    expect(res.data.geoAddrLatitude).eq(30.2788218671027600);
    expect(res.data.geoAddrLongitude).eq(120.1631599554121500);
    expect(res.data.addrFloorLine).eq('汉庭酒店');
    expect(res.data.addrBlock).to.be.null;
    expect(res.data.addrStreetAddress).eq('大家苑社区 朝晖路198号 汉庭酒店');
    expect(res.data.addrDistrict).eq('西溪街道');
    expect(res.data.addrArea).eq('下城区');
    expect(res.data.addrPostalCode).eq('100029');
    expect(res.data.addrCity).eq('杭州');
    expect(res.data.addrProvince).eq('江浙');
    expect(res.data.addrState).to.be.null;
    expect(res.data.addrCountryCode).eq('cn');
    expect(res.data.active).eq(true);
  });

  it('could modify target record by id', async function(){
    // query target property
    let res = await axios.get(`${apiBaseUrl}/property/24009`);
    expect(res.data).is.an('object');
    expect(res.data.id).to.match(numberOnlyPattern).that.exist;
    expect(res.data.version).eq(1);
    expect(res.data.creationDate).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.lastUpdated).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.name).eq('汉庭酒店');
    expect(res.data.ownerId).eq('33014');
    expect(res.data.ownershipDate).eq('2020-05-20');
    expect(res.data.geoAddrLatitude).eq(30.2788218671027600);
    expect(res.data.geoAddrLongitude).eq(120.1631599554121500);
    expect(res.data.addrFloorLine).eq('汉庭酒店');
    expect(res.data.addrBlock).to.be.null;
    expect(res.data.addrStreetAddress).eq('大家苑社区 朝晖路198号 汉庭酒店');
    expect(res.data.addrDistrict).eq('西溪街道');
    expect(res.data.addrArea).eq('下城区');
    expect(res.data.addrPostalCode).eq('100029');
    expect(res.data.addrCity).eq('杭州');
    expect(res.data.addrProvince).eq('江浙');
    expect(res.data.addrState).to.be.null;
    expect(res.data.addrCountryCode).eq('cn');
    expect(res.data.active).eq(true);

    // modify property
    let data: any = {
      id: '24009',
      version: 1,
      name: "汉庭酒店v",
      ownerId: "33019",
      ownershipDate: "2020-05-29",
      geoAddrLatitude: 39.2788218671027600,
      geoAddrLongitude: 129.1631599554121500,
      addrFloorLine: "汉庭酒店vv",
      addrBlock: "0栋",
      addrStreetAddress: "大家苑社区 朝晖路198号 汉庭酒店9",
      addrDistrict: "西溪街道9",
      addrArea: "下城区9",
      addrPostalCode: "100099",
      addrCity: "杭州9",
      addrProvince: "江浙9",
      addrState: "无州",
      addrCountryCode: "cn9",
      active: false
    };
    res = await axios.put(`${apiBaseUrl}/property/24009`, data);
    expect(res.data.version).eq(2);
    expect(res.data.creationDate).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.lastUpdated).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.name).eq('汉庭酒店v');
    expect(res.data.ownerId).eq('33019');
    expect(res.data.ownershipDate).eq('2020-05-29');
    expect(res.data.geoAddrLatitude).eq(39.2788218671027600);
    expect(res.data.geoAddrLongitude).eq(129.1631599554121500);
    expect(res.data.addrFloorLine).eq('汉庭酒店vv');
    expect(res.data.addrBlock).eq('0栋');
    expect(res.data.addrStreetAddress).eq('大家苑社区 朝晖路198号 汉庭酒店9');
    expect(res.data.addrDistrict).eq('西溪街道9');
    expect(res.data.addrArea).eq('下城区9');
    expect(res.data.addrPostalCode).eq('100099');
    expect(res.data.addrCity).eq('杭州9');
    expect(res.data.addrProvince).eq('江浙9');
    expect(res.data.addrState).eq('无州');
    expect(res.data.addrCountryCode).eq('cn9');
    expect(res.data.active).eq(false);

    // query target property
    res = await axios.get(`${apiBaseUrl}/property/24009`);
    expect(res.data.version).eq(2);
    expect(res.data.name).eq('汉庭酒店v');
    expect(res.data.ownerId).eq('33019');
    expect(res.data.ownershipDate).eq('2020-05-29');
    expect(res.data.geoAddrLatitude).eq(39.2788218671027600);
    expect(res.data.geoAddrLongitude).eq(129.1631599554121500);
    expect(res.data.addrFloorLine).eq('汉庭酒店vv');
    expect(res.data.addrBlock).eq('0栋');
    expect(res.data.addrStreetAddress).eq('大家苑社区 朝晖路198号 汉庭酒店9');
    expect(res.data.addrDistrict).eq('西溪街道9');
    expect(res.data.addrArea).eq('下城区9');
    expect(res.data.addrPostalCode).eq('100099');
    expect(res.data.addrCity).eq('杭州9');
    expect(res.data.addrProvince).eq('江浙9');
    expect(res.data.addrState).eq('无州');
    expect(res.data.addrCountryCode).eq('cn9');
    expect(res.data.active).eq(false);

    // modify property
    data = {
      "id": "24009",
      "version": 2,
      "creationDate": "2021-06-10T07:25:46Z",
      "lastUpdated": "2021-06-10T07:25:46Z",
      "name": "汉庭酒店",
      "ownerId": "33014",
      "ownershipDate": "2020-05-20",
      "geoAddrLatitude": 30.2788218671027600,
      "geoAddrLongitude": 120.1631599554121500,
      "addrFloorLine": "汉庭酒店",
      "addrBlock": null,
      "addrStreetAddress": "大家苑社区 朝晖路198号 汉庭酒店",
      "addrDistrict": "西溪街道",
      "addrArea": "下城区",
      "addrPostalCode": "100029",
      "addrCity": "杭州",
      "addrProvince": "江浙",
      "addrState": null,
      "addrCountryCode": "cn",
      "active": true
    };
    res = await axios.put(`${apiBaseUrl}/property/24009`, data);
    expect(res.data.version).eq(3);
    expect(res.data.creationDate).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.lastUpdated).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.name).eq('汉庭酒店');
    expect(res.data.ownerId).eq('33014');
    expect(res.data.ownershipDate).eq('2020-05-20');
    expect(res.data.geoAddrLatitude).eq(30.2788218671027600);
    expect(res.data.geoAddrLongitude).eq(120.1631599554121500);
    expect(res.data.addrFloorLine).eq('汉庭酒店');
    expect(res.data.addrBlock).to.be.null;
    expect(res.data.addrStreetAddress).eq('大家苑社区 朝晖路198号 汉庭酒店');
    expect(res.data.addrDistrict).eq('西溪街道');
    expect(res.data.addrArea).eq('下城区');
    expect(res.data.addrPostalCode).eq('100029');
    expect(res.data.addrCity).eq('杭州');
    expect(res.data.addrProvince).eq('江浙');
    expect(res.data.addrState).to.be.null;
    expect(res.data.addrCountryCode).eq('cn');
    expect(res.data.active).eq(true);

    // query target property
    res = await axios.get(`${apiBaseUrl}/property/24009`);
    expect(res.data.version).eq(3);
    expect(res.data.creationDate).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.lastUpdated).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data.name).eq('汉庭酒店');
    expect(res.data.ownerId).eq('33014');
    expect(res.data.ownershipDate).eq('2020-05-20');
    expect(res.data.geoAddrLatitude).eq(30.2788218671027600);
    expect(res.data.geoAddrLongitude).eq(120.1631599554121500);
    expect(res.data.addrFloorLine).eq('汉庭酒店');
    expect(res.data.addrBlock).to.be.null;
    expect(res.data.addrStreetAddress).eq('大家苑社区 朝晖路198号 汉庭酒店');
    expect(res.data.addrDistrict).eq('西溪街道');
    expect(res.data.addrArea).eq('下城区');
    expect(res.data.addrPostalCode).eq('100029');
    expect(res.data.addrCity).eq('杭州');
    expect(res.data.addrProvince).eq('江浙');
    expect(res.data.addrState).to.be.null;
    expect(res.data.addrCountryCode).eq('cn');
    expect(res.data.active).eq(true);
  });

  it('reject when find target by invalid id', async function(){
    // query target property
    let resError = await axios.get(`${apiBaseUrl}/property/24009-not-exists`).then(res=>{
      return 'endpoint reject expected';
    }).catch(err=>{
      expect(err.response.status).eq(400);
      return err.response.data;
    });
    expect(resError).is.an('object');
    expect(resError.errorCode).eq('RECORD_NOT_FOUND');
  });

  it('reject modify record with inconsistent id', async function(){
    // query target property
    let res = await axios.get(`${apiBaseUrl}/property/24010`);
    expect(res.data).is.an('object');
    expect(res.data.id).to.match(numberOnlyPattern);
    expect(res.data.version).eq(1);

    // modify property
    let data: any = {
      id: '24011',
      version: 1,
      name: "汉庭酒店",
      ownerId: "33019",
      addrCity: "杭州",
      addrCountryCode: "cn",
    };
    data.name = 'something else';
    let resError = await axios.put(`${apiBaseUrl}/property/24010`, data).then(res=>{
      return 'endpoint reject expected';
    }).catch(err=>{
      expect(err.response.status).eq(400);
      return err.response.data;
    });
    expect(resError).is.an('object');
    expect(resError.errorCode).eq('INVALID_USE_ERROR');
  });

  it('could list many record', async function(){
    // query property with paging
    let res = await axios.get(`${apiBaseUrl}/property`);
    expect(res.data).is.an('array');
    expect(res.data).to.have.length.at.least(6);
    expect(res.data[0]).is.an('object');
    expect(res.data[1]).is.an('object');
    expect(res.data[2]).is.an('object');
    expect(res.data[9]).is.an('object');
    expect(res.data[0].id).to.match(numberOnlyPattern).that.exist;
    expect(res.data[0].version).at.least(1);
    expect(res.data[0].creationDate).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data[0].lastUpdated).to.match(dateTimeNoZonePattern).that.exist;
    expect(res.data[0].name).match(alphanumericOrChinese);
    expect(res.data[1].name).match(alphanumericOrChinese);
    expect(res.data[2].name).match(alphanumericOrChinese);
    expect(res.data[9].name).match(alphanumericOrChinese);
  });

  it('could list record as specific page size 2', async function(){
    // query property with paging
    let res = await axios.get(`${apiBaseUrl}/property?size=2`);
    expect(res.data).is.an('array');
    expect(res.data).to.have.length(2);
    expect(res.data[0]).is.an('object');
    expect(res.data[1]).is.an('object');
  });

  it('could list record as specific page size', async function(){
    // query property with paging
    let res = await axios.get(`${apiBaseUrl}/property?size=5`);
    expect(res.data).is.an('array');
    expect(res.data).to.have.length(5);
    expect(res.data[0]).is.an('object');
    expect(res.data[1]).is.an('object');
  });

  it('could list record as specific page offset', async function(){
    // query property with paging
    let res = await axios.get(`${apiBaseUrl}/property?offset=4&size=2`);
    expect(res.data).is.an('array');
    expect(res.data).to.have.length(2);
    expect(res.data[0]).is.an('object');
    expect(res.data[1]).is.an('object');
    expect(res.data[0].id).eq('24005');
    expect(res.data[1].id).eq('24006');
  });

});
