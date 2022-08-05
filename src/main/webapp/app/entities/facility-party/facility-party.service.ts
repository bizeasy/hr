import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityParty } from 'app/shared/model/facility-party.model';

type EntityResponseType = HttpResponse<IFacilityParty>;
type EntityArrayResponseType = HttpResponse<IFacilityParty[]>;

@Injectable({ providedIn: 'root' })
export class FacilityPartyService {
  public resourceUrl = SERVER_API_URL + 'api/facility-parties';

  constructor(protected http: HttpClient) {}

  create(facilityParty: IFacilityParty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityParty);
    return this.http
      .post<IFacilityParty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(facilityParty: IFacilityParty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityParty);
    return this.http
      .put<IFacilityParty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFacilityParty>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFacilityParty[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(facilityParty: IFacilityParty): IFacilityParty {
    const copy: IFacilityParty = Object.assign({}, facilityParty, {
      fromDate: facilityParty.fromDate && facilityParty.fromDate.isValid() ? facilityParty.fromDate.toJSON() : undefined,
      thruDate: facilityParty.thruDate && facilityParty.thruDate.isValid() ? facilityParty.thruDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((facilityParty: IFacilityParty) => {
        facilityParty.fromDate = facilityParty.fromDate ? moment(facilityParty.fromDate) : undefined;
        facilityParty.thruDate = facilityParty.thruDate ? moment(facilityParty.thruDate) : undefined;
      });
    }
    return res;
  }
}
