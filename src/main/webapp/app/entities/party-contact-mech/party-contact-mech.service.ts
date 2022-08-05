import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyContactMech } from 'app/shared/model/party-contact-mech.model';

type EntityResponseType = HttpResponse<IPartyContactMech>;
type EntityArrayResponseType = HttpResponse<IPartyContactMech[]>;

@Injectable({ providedIn: 'root' })
export class PartyContactMechService {
  public resourceUrl = SERVER_API_URL + 'api/party-contact-meches';

  constructor(protected http: HttpClient) {}

  create(partyContactMech: IPartyContactMech): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyContactMech);
    return this.http
      .post<IPartyContactMech>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyContactMech: IPartyContactMech): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyContactMech);
    return this.http
      .put<IPartyContactMech>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyContactMech>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyContactMech[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partyContactMech: IPartyContactMech): IPartyContactMech {
    const copy: IPartyContactMech = Object.assign({}, partyContactMech, {
      fromDate: partyContactMech.fromDate && partyContactMech.fromDate.isValid() ? partyContactMech.fromDate.toJSON() : undefined,
      thruDate: partyContactMech.thruDate && partyContactMech.thruDate.isValid() ? partyContactMech.thruDate.toJSON() : undefined,
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
      res.body.forEach((partyContactMech: IPartyContactMech) => {
        partyContactMech.fromDate = partyContactMech.fromDate ? moment(partyContactMech.fromDate) : undefined;
        partyContactMech.thruDate = partyContactMech.thruDate ? moment(partyContactMech.thruDate) : undefined;
      });
    }
    return res;
  }
}
