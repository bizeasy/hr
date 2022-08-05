import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyClassification } from 'app/shared/model/party-classification.model';

type EntityResponseType = HttpResponse<IPartyClassification>;
type EntityArrayResponseType = HttpResponse<IPartyClassification[]>;

@Injectable({ providedIn: 'root' })
export class PartyClassificationService {
  public resourceUrl = SERVER_API_URL + 'api/party-classifications';

  constructor(protected http: HttpClient) {}

  create(partyClassification: IPartyClassification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyClassification);
    return this.http
      .post<IPartyClassification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyClassification: IPartyClassification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyClassification);
    return this.http
      .put<IPartyClassification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyClassification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyClassification[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partyClassification: IPartyClassification): IPartyClassification {
    const copy: IPartyClassification = Object.assign({}, partyClassification, {
      fromDate: partyClassification.fromDate && partyClassification.fromDate.isValid() ? partyClassification.fromDate.toJSON() : undefined,
      thruDate: partyClassification.thruDate && partyClassification.thruDate.isValid() ? partyClassification.thruDate.toJSON() : undefined,
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
      res.body.forEach((partyClassification: IPartyClassification) => {
        partyClassification.fromDate = partyClassification.fromDate ? moment(partyClassification.fromDate) : undefined;
        partyClassification.thruDate = partyClassification.thruDate ? moment(partyClassification.thruDate) : undefined;
      });
    }
    return res;
  }
}
