import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityContactMech } from 'app/shared/model/facility-contact-mech.model';

type EntityResponseType = HttpResponse<IFacilityContactMech>;
type EntityArrayResponseType = HttpResponse<IFacilityContactMech[]>;

@Injectable({ providedIn: 'root' })
export class FacilityContactMechService {
  public resourceUrl = SERVER_API_URL + 'api/facility-contact-meches';

  constructor(protected http: HttpClient) {}

  create(facilityContactMech: IFacilityContactMech): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityContactMech);
    return this.http
      .post<IFacilityContactMech>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(facilityContactMech: IFacilityContactMech): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityContactMech);
    return this.http
      .put<IFacilityContactMech>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFacilityContactMech>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFacilityContactMech[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(facilityContactMech: IFacilityContactMech): IFacilityContactMech {
    const copy: IFacilityContactMech = Object.assign({}, facilityContactMech, {
      fromDate: facilityContactMech.fromDate && facilityContactMech.fromDate.isValid() ? facilityContactMech.fromDate.toJSON() : undefined,
      thruDate: facilityContactMech.thruDate && facilityContactMech.thruDate.isValid() ? facilityContactMech.thruDate.toJSON() : undefined,
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
      res.body.forEach((facilityContactMech: IFacilityContactMech) => {
        facilityContactMech.fromDate = facilityContactMech.fromDate ? moment(facilityContactMech.fromDate) : undefined;
        facilityContactMech.thruDate = facilityContactMech.thruDate ? moment(facilityContactMech.thruDate) : undefined;
      });
    }
    return res;
  }
}
